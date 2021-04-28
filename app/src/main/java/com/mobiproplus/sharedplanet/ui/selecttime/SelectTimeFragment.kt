package com.mobiproplus.sharedplanet.ui.selecttime

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mobiproplus.sharedplanet.R
import com.mobiproplus.sharedplanet.utils.showLongToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_select_time.*
import kotlinx.android.synthetic.main.fragment_select_time.progress

@AndroidEntryPoint
class SelectTimeFragment : Fragment() {

    private val selectTimeViewModel: SelectTimeViewModel by viewModels()

    private val args: SelectTimeFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_select_time, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val selectPhotoAdapter = SelectTimeAdapter(SelectTimeAdapter.NasaTimeListener { imageUrl ->
            selectTimeViewModel.onTimeClicked(imageUrl)
        })
        listWithDates.adapter = selectPhotoAdapter
        args.date.let { selectTimeViewModel.getNasaPhotos(it!!) }

        selectTimeViewModel.navigateToPhoto.observe(viewLifecycleOwner, Observer { imageUrl ->
            imageUrl?.let {
                this.findNavController().navigate(
                    SelectTimeFragmentDirections
                        .actionSelectPhotoFragmentToShowPhotoFragment(it)
                )
                selectTimeViewModel.onPhotoNavigated()
            }
        })

        selectTimeViewModel.photos.observe(viewLifecycleOwner, Observer { nasaPhotos ->
            progress.visibility = View.GONE
            selectPhotoAdapter.data = nasaPhotos
        })

        selectTimeViewModel.toastMessage.observe(viewLifecycleOwner, Observer { message ->
            message?.let {
                progress.visibility = View.GONE
                showLongToast(it)
                 selectTimeViewModel.onToastShown()
            }
        })
    }
}