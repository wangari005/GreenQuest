package com.greenquest.app.ui.viewmodel;

import com.google.firebase.database.FirebaseDatabase;
import com.greenquest.app.data.repository.UserRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class CommunityViewModel_Factory implements Factory<CommunityViewModel> {
  private final Provider<UserRepository> userRepositoryProvider;

  private final Provider<FirebaseDatabase> databaseProvider;

  public CommunityViewModel_Factory(Provider<UserRepository> userRepositoryProvider,
      Provider<FirebaseDatabase> databaseProvider) {
    this.userRepositoryProvider = userRepositoryProvider;
    this.databaseProvider = databaseProvider;
  }

  @Override
  public CommunityViewModel get() {
    return newInstance(userRepositoryProvider.get(), databaseProvider.get());
  }

  public static CommunityViewModel_Factory create(Provider<UserRepository> userRepositoryProvider,
      Provider<FirebaseDatabase> databaseProvider) {
    return new CommunityViewModel_Factory(userRepositoryProvider, databaseProvider);
  }

  public static CommunityViewModel newInstance(UserRepository userRepository,
      FirebaseDatabase database) {
    return new CommunityViewModel(userRepository, database);
  }
}
