package kim.uno.simpleapp.ui.document

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kim.uno.simpleapp.databinding.DocumentFragmentBinding
import kim.uno.simpleapp.ui.BaseFragment
import kim.uno.simpleapp.util.GlideListener
import kim.uno.simpleapp.util.observe
import kim.uno.simpleapp.util.visible

@AndroidEntryPoint
class DocumentFragment : BaseFragment() {

    private lateinit var binding: DocumentFragmentBinding
    private val documentViewModel by viewModels<DocumentViewModel>()
    private val navArgs by navArgs<DocumentFragmentArgs>()

    override fun onCreateViewOnce(inflater: LayoutInflater): View {
        binding = DocumentFragmentBinding.inflate(inflater)
        val document = navArgs.document
        binding.viewModel = documentViewModel.apply { setup(document) }
        binding.back.setOnClickListener { it.findNavController().popBackStack() }
        binding.favorite.setOnClickListener { documentViewModel.toggleFavorite() }

        Glide.with(requireContext())
            .load(document.thumbnail)
            .listener(GlideListener<Drawable> {
                startPostponedEnterTransition()
            })
            .into(binding.image)

        observe(documentViewModel.favorite) { binding.favorite.isSelected = it }
        observe(documentViewModel.progress) { binding.progress.visible = it }

        installTransition()
        postponeEnterTransition()
        return binding.root
    }

}