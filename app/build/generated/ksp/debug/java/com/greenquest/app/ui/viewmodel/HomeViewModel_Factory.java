package com.greenquest.app.ui.viewmodel;

import com.greenquest.app.data.repository.ActionRepository;
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
public final class HomeViewModel_Factory implements Factory<HomeViewModel> {
  private final Provider<ActionRepository> actionRepositoryProvider;

  private final Provider<UserRepository> userRepositoryProvider;

  public HomeViewModel_Factory(Provider<ActionRepository> actionRepositoryProvider,
      Provider<UserRepository> userRepositoryProvider) {
    this.actionRepositoryProvider = actionRepositoryProvider;
    this.userRepositoryProvider = userRepositoryProvider;
  }

  @Override
  public HomeViewModel get() {
    return newInstance(actionRepositoryProvider.get(), userRepositoryProvider.get());
  }

  public static HomeViewModel_Factory create(Provider<ActionRepository> actionRepositoryProvider,
      Provider<UserRepository> userRepositoryProvider) {
    return new HomeViewModel_Factory(actionRepositoryProvider, userRepositoryProvider);
  }

  public static HomeViewModel newInstance(ActionRepository actionRepository,
      UserRepository userRepository) {
    return new HomeViewModel(actionRepository, userRepository);
  }
}
