package com.hamon.albochallengenormal.binding

import android.view.View
import coil.load
import com.hamon.albochallengenormal.R
import com.hamon.albochallengenormal.databinding.CharacterItemBinding
import com.hamon.albochallengenormal.features.exercises_practice_code.domain.entities.RickAndMortyCharacter
import com.xwray.groupie.viewbinding.BindableItem

class CharacterItem(private val character: RickAndMortyCharacter) :
    BindableItem<CharacterItemBinding>() {
    override fun bind(viewBinding: CharacterItemBinding, position: Int) {
        viewBinding.apply {
            nameCharacter.text = character.name
            imageCharacter.load(character.image)
        }
    }

    override fun getLayout(): Int = R.layout.character_item

    override fun initializeViewBinding(view: View): CharacterItemBinding =
        CharacterItemBinding.bind(view)
}