package com.greenquest.app.di;

import android.content.Context;
import com.greenquest.app.data.prefs.ProfileDataStore;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class AppModule_ProvideProfileDataStoreFactory implements Factory<ProfileDataStore> {
  private final Provider<Context> contextProvider;

  public AppModule_ProvideProfileDataStoreFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public ProfileDataStore get() {
    return provideProfileDataStore(contextProvider.get());
  }

  public static AppModule_ProvideProfileDataStoreFactory create(Provider<Context> contextProvider) {
    return new AppModule_ProvideProfileDataStoreFactory(contextProvider);
  }

  public static ProfileDataStore provideProfileDataStore(Context context) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideProfileDataStore(context));
  }
}
