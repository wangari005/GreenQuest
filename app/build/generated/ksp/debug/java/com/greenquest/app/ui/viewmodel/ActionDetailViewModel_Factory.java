package com.greenquest.app.ui.viewmodel;

import androidx.lifecycle.SavedStateHandle;
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
public final class ActionDetailViewModel_Factory implements Factory<ActionDetailViewModel> {
  private final Provider<ActionRepository> repoProvider;

  private final Provider<UserRepository> userRepositoryProvider;

  private final Provider<SavedStateHandle> savedStateHandleProvider;

  public ActionDetailViewModel_Factory(Provider<ActionRepository> repoProvider,
      Provider<UserRepository> userRepositoryProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    this.repoProvider = repoProvider;
    this.userRepositoryProvider = userRepositoryProvider;
    this.savedStateHandleProvider = savedStateHandleProvider;
  }

  @Override
  public ActionDetailViewModel get() {
    return newInstance(repoProvider.get(), userRepositoryProvider.get(), savedStateHandleProvider.get());
  }

  public static ActionDetailViewModel_Factory create(Provider<ActionRepository> repoProvider,
      Provider<UserRepository> userRepositoryProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    return new ActionDetailViewModel_Factory(repoProvider, userRepositoryProvider, savedStateHandleProvider);
  }

  public static ActionDetailViewModel newInstance(ActionRepository repo,
      UserRepository userRepository, SavedStateHandle savedStateHandle) {
    return new ActionDetailViewModel(repo, userRepository, savedStateHandle);
  }
}
