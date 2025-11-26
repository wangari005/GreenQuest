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
public final class SplashViewModel_Factory implements Factory<SplashViewModel> {
  private final Provider<ProfileDataStore> prefsProvider;

  public SplashViewModel_Factory(Provider<ProfileDataStore> prefsProvider) {
    this.prefsProvider = prefsProvider;
  }

  @Override
  public SplashViewModel get() {
    return newInstance(prefsProvider.get());
  }

  public static SplashViewModel_Factory create(Provider<ProfileDataStore> prefsProvider) {
    return new SplashViewModel_Factory(prefsProvider);
  }

  public static SplashViewModel newInstance(ProfileDataStore prefs) {
    return new SplashViewModel(prefs);
  }
}
