package com.valance.medicine.ui.presenter

import com.google.firebase.firestore.FirebaseFirestore
import com.valance.medicine.ui.view.ProfessionContract

class ProfessionPresenter(private val view: ProfessionContract.View) {

    class Profession(
        var names: List<*>? = null
    )

    fun getDataFromFirebase(){
        val db = FirebaseFirestore.getInstance()
        db.collection("Profession")
            .get()
            .addOnSuccessListener { documents ->
                val professions = mutableListOf<Profession>()
                for (document in documents) {
                    val names = document.get("name") as? List<*> // То менять здесь :3
                    val profession = Profession(names)
                    professions.add(profession)
                }
                val professionNames = professions.flatMap { it.names ?: emptyList() }
                view.showData(professionNames)
            }
    }

}
