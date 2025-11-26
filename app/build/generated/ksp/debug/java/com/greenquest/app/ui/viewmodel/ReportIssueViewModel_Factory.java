package com.greenquest.app.ui.viewmodel;

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
public final class ReportIssueViewModel_Factory implements Factory<ReportIssueViewModel> {
  private final Provider<UserRepository> userRepositoryProvider;

  public ReportIssueViewModel_Factory(Provider<UserRepository> userRepositoryProvider) {
    this.userRepositoryProvider = userRepositoryProvider;
  }

  @Override
  public ReportIssueViewModel get() {
    return newInstance(userRepositoryProvider.get());
  }

  public static ReportIssueViewModel_Factory create(
      Provider<UserRepository> userRepositoryProvider) {
    return new ReportIssueViewModel_Factory(userRepositoryProvider);
  }

  public static ReportIssueViewModel newInstance(UserRepository userRepository) {
    return new ReportIssueViewModel(userRepository);
  }
}
