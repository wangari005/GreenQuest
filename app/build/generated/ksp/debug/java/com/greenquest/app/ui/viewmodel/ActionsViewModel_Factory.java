package com.greenquest.app.ui.viewmodel;

import com.greenquest.app.data.repo.ActionRepository;
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
public final class ActionsViewModel_Factory implements Factory<ActionsViewModel> {
  private final Provider<ActionRepository> repoProvider;

  public ActionsViewModel_Factory(Provider<ActionRepository> repoProvider) {
    this.repoProvider = repoProvider;
  }

  @Override
  public ActionsViewModel get() {
    return newInstance(repoProvider.get());
  }

  public static ActionsViewModel_Factory create(Provider<ActionRepository> repoProvider) {
    return new ActionsViewModel_Factory(repoProvider);
  }

  public static ActionsViewModel newInstance(ActionRepository repo) {
    return new ActionsViewModel(repo);
  }
}
