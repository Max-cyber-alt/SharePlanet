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
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.davemorrissey.labs.subscaleview.ImageSource
import com.mobiproplus.sharedplanet.R
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener
import kotlinx.android.synthetic.main.fragment_photo.*

class PhotoFragment : Fragment() {

    private val args: PhotoFragmentArgs by navArgs()
    private var photo: Bitmap? = null

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                performSharing()
            } else {
                showStorageWarning()
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
                    showLoadingWarning()
                }
                return true
            }
            R.id.action_share -> {
                if (photo != null) {
                    performSharing()
                } else {
                    showLoadingWarning()
                }
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun loadImage() {
        ImageLoader.getInstance().loadImage(args.photoUrl, object : SimpleImageLoadingListener() {
            override fun onLoadingComplete(imageUri: String, view: View?, loadedImage: Bitmap) {
                if (!activity?.isFinishing!!) {
                    photo = loadedImage
                    nasaImage.setImage(ImageSource.cachedBitmap(loadedImage))
                    progress.visibility = View.GONE
                }
            }
        })
    }

    private fun setWallpaperBitmap() {
        val wallpaperManager =
            WallpaperManager.getInstance(activity?.applicationContext)
        wallpaperManager.setBitmap(photo)
        Toast.makeText(context, R.string.set_as_wallpaper_completed, Toast.LENGTH_SHORT).show()
    }

    private fun performSharing() {
        if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
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

    private fun showLoadingWarning() {
        Toast.makeText(context, getString(R.string.photo_is_loading), Toast.LENGTH_SHORT).show()
    }

    private fun showStorageWarning() {
        Toast.makeText(context, getString(R.string.permission_denied), Toast.LENGTH_SHORT).show()
    }
}