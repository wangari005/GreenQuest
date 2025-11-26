package com.greenquest.app.di;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.greenquest.app.data.repository.UserRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class AppModule_ProvideUserRepositoryFactory implements Factory<UserRepository> {
  private final Provider<FirebaseAuth> authProvider;

  private final Provider<FirebaseDatabase> databaseProvider;

  public AppModule_ProvideUserRepositoryFactory(Provider<FirebaseAuth> authProvider,
      Provider<FirebaseDatabase> databaseProvider) {
    this.authProvider = authProvider;
    this.databaseProvider = databaseProvider;
  }

  @Override
  public UserRepository get() {
    return provideUserRepository(authProvider.get(), databaseProvider.get());
  }

  public static AppModule_ProvideUserRepositoryFactory create(Provider<FirebaseAuth> authProvider,
      Provider<FirebaseDatabase> databaseProvider) {
    return new AppModule_ProvideUserRepositoryFactory(authProvider, databaseProvider);
  }

  public static UserRepository provideUserRepository(FirebaseAuth auth, FirebaseDatabase database) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideUserRepository(auth, database));
  }
}
