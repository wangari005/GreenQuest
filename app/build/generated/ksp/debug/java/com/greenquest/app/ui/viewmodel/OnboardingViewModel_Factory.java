package com.greenquest.app.ui.viewmodel;

import com.greenquest.app.data.prefs.ProfileDataStore;
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
public final class OnboardingViewModel_Factory implements Factory<OnboardingViewModel> {
  private final Provider<ProfileDataStore> prefsProvider;

  public OnboardingViewModel_Factory(Provider<ProfileDataStore> prefsProvider) {
    this.prefsProvider = prefsProvider;
  }

  @Override
  public OnboardingViewModel get() {
    return newInstance(prefsProvider.get());
  }

  public static OnboardingViewModel_Factory create(Provider<ProfileDataStore> prefsProvider) {
    return new OnboardingViewModel_Factory(prefsProvider);
  }

  public static OnboardingViewModel newInstance(ProfileDataStore prefs) {
    return new OnboardingViewModel(prefs);
  }
}
