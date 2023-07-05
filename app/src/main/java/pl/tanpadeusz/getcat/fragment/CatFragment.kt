package pl.tanpadeusz.getcat.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.squareup.picasso.Picasso
import pl.tanpadeusz.getcat.databinding.FragmentCatBinding
import pl.tanpadeusz.getcat.viewmodel.CatViewModel
import timber.log.Timber

class CatFragment : Fragment() {
    private lateinit var binding : FragmentCatBinding
    private val cvm : CatViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        this.binding = FragmentCatBinding.inflate(inflater, container, false)
        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.setupObservers()
        this.setupButtons()
        this.requestImage()
    }

    private fun setupButtons() {
        this.binding.buttonLike.isEnabled = false
        this.binding.buttonDislike.isEnabled = false

        this.binding.buttonNext.setOnClickListener {
            Timber.d("next button clicked")
            this.requestImage()
        }
    }

    private fun setupObservers() {
        this.cvm.imageResponse.observe(this.viewLifecycleOwner) {
            if (it != null)
                this.loadImage(it.url)
        }
    }

    private fun loadImage(url: String) {
        Picasso.get()
            .load(url)
            .fit()
            .centerInside()
            .into(this.binding.catImageView)
    }

    private fun requestImage() {
        this.cvm.requestImage()
    }
}