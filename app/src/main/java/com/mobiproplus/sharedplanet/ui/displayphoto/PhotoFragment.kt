package com.mobiproplus.sharedplanet.ui.displayphoto

import android.Manifest
import android.app.WallpaperManager
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import android.widget.ProgressBar
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.mobiproplus.sharedplanet.R
import com.mobiproplus.sharedplanet.utils.showShortToast
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener

class PhotoFragment : Fragment() {

    private val args: PhotoFragmentArgs by navArgs()
    private var photo: Bitmap? = null

    private lateinit var nasaImage: SubsamplingScaleImageView
    private lateinit var progress: ProgressBar

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                performSharing()
            } else {
                showShortToast(getString(R.string.permission_denied))
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_photo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        nasaImage = view.findViewById(R.id.nasaImage)
        progress = view.findViewById(R.id.progress)

        loadImage()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_photo, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_set_wallpaper -> {
                if (photo != null) {
                    setWallpaperBitmap()
                } else {
                    showShortToast(getString(R.string.photo_is_loading))
                }
                return true
            }
            R.id.action_share -> {
                if (photo != null) {
                    performSharing()
                } else {
                    showShortToast(getString(R.string.photo_is_loading))
                }
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun loadImage() {
        ImageLoader.getInstance().loadImage(args.photoUrl, object : SimpleImageLoadingListener() {
            override fun onLoadingComplete(imageUri: String, view: View?, loadedImage: Bitmap) {
                photo = loadedImage
                nasaImage.setImage(ImageSource.cachedBitmap(loadedImage))
                progress.visibility = View.GONE
            }

            override fun onLoadingCancelled(imageUri: String?, view: View?) {
                loadImage()
            }
        })
    }

    private fun setWallpaperBitmap() {
        val wallpaperManager =
            WallpaperManager.getInstance(activity?.applicationContext)
        wallpaperManager.setBitmap(photo)
        showShortToast(getString(R.string.set_as_wallpaper_completed))
    }

    private fun performSharing() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val path = MediaStore.Images.Media.insertImage(
                activity?.contentResolver, photo, args.photoUrl, ""
            )
            val uri = Uri.parse(path)
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "image/jpeg"
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            startActivity(Intent.createChooser(intent, getString(R.string.share)))
        } else {
            requestPermission.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }
}