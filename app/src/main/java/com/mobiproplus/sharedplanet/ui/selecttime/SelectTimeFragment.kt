package com.mobiproplus.sharedplanet.ui.selecttime

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.mobiproplus.sharedplanet.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_select_time.*
import kotlinx.android.synthetic.main.fragment_select_time.progress
import javax.inject.Inject

@AndroidEntryPoint
class SelectTimeFragment : Fragment() {

    @Inject
    lateinit var viewModel: SelectTimeViewModel

    private val args: SelectTimeFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_select_time, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val selectPhotoAdapter = SelectTimeAdapter()
        listWithDates.adapter = selectPhotoAdapter
        args.date.let { viewModel.getNasaPhotos(it!!) }

        viewModel.nasaPhotos.observe(viewLifecycleOwner, Observer { nasaPhotos ->
            progress.visibility = View.GONE
            selectPhotoAdapter.data = nasaPhotos
        })

        viewModel.toastMessage.observe(viewLifecycleOwner, Observer { text ->
            text?.let {
                progress.visibility = View.GONE
                Toast.makeText(context, text, Toast.LENGTH_LONG).show()
                viewModel.onToastShown()
            }
        })
    }
}