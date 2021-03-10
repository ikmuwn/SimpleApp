package kim.uno.simpleapp.ui.document

import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kim.uno.simpleapp.databinding.DocumentFragmentBinding
import kim.uno.simpleapp.ui.BaseFragment
import kim.uno.simpleapp.extension.finally

@AndroidEntryPoint
class DocumentFragment : BaseFragment() {

    private lateinit var binding: DocumentFragmentBinding
    private val documentViewModel by viewModels<DocumentViewModel>()
    private val navArgs by navArgs<DocumentFragmentArgs>()

    override fun onCreateViewOnce(inflater: LayoutInflater): View {
        binding = DocumentFragmentBinding.inflate(inflater)
        val document = navArgs.document
        binding.viewModel = documentViewModel.apply { setup(document) }
        binding.lifecycleOwner = this
        binding.back.setOnClickListener { it.findNavController().popBackStack() }
        binding.favorite.setOnClickListener { documentViewModel.toggleFavorite() }

        Glide.with(requireContext())
            .load(document.thumbnail)
            .finally {
                startPostponedEnterTransition()
                false
            }
            .into(binding.image)

        installTransition()
        postponeEnterTransition()
        return binding.root
    }

}