package com.valance.medicine.di

import com.google.firebase.firestore.FirebaseFirestore
import com.valance.medicine.data.repository.DoctorRepository
import com.valance.medicine.data.source.FirestoreDoctorDataSource
import com.valance.medicine.ui.adapter.DoctorAdapter
import com.valance.medicine.ui.fragment.OrderFragment
import com.valance.medicine.ui.presenter.DoctorPresenter
import com.valance.medicine.ui.view.DoctorContractInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ReposModule {

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirestoreDoctorDataSource(firestore: FirebaseFirestore): FirestoreDoctorDataSource {
        return FirestoreDoctorDataSource(firestore)
    }

    @Provides
    @Singleton
    fun provideDoctorRepository(dataSource: FirestoreDoctorDataSource): DoctorRepository {
        return DoctorRepository(dataSource)
    }
    @Provides
    fun provideDoctorAdapter(): DoctorAdapter {
        return DoctorAdapter()
    }

    @Provides
    fun provideDoctorPresenter(repository: DoctorRepository): DoctorContractInterface.Presenter {
        return DoctorPresenter(repository)
    }

    @Provides
    fun provideDoctorView(doctorActivity: OrderFragment): DoctorContractInterface.View {
        return doctorActivity
    }
}
