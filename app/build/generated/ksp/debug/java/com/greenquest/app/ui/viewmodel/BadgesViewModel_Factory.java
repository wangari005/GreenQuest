package com.greenquest.app.ui.viewmodel;

import com.greenquest.app.data.repo.ActionRepository;
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
public final class BadgesViewModel_Factory implements Factory<BadgesViewModel> {
  private final Provider<ActionRepository> repoProvider;

  private final Provider<UserRepository> userRepositoryProvider;

  public BadgesViewModel_Factory(Provider<ActionRepository> repoProvider,
      Provider<UserRepository> userRepositoryProvider) {
    this.repoProvider = repoProvider;
    this.userRepositoryProvider = userRepositoryProvider;
  }

  @Override
  public BadgesViewModel get() {
    return newInstance(repoProvider.get(), userRepositoryProvider.get());
  }

  public static BadgesViewModel_Factory create(Provider<ActionRepository> repoProvider,
      Provider<UserRepository> userRepositoryProvider) {
    return new BadgesViewModel_Factory(repoProvider, userRepositoryProvider);
  }

  public static BadgesViewModel newInstance(ActionRepository repo, UserRepository userRepository) {
    return new BadgesViewModel(repo, userRepository);
  }
}
