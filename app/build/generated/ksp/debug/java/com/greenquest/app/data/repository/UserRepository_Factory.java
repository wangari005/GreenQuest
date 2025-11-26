package com.greenquest.app.data.repository;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class UserRepository_Factory implements Factory<UserRepository> {
  private final Provider<FirebaseAuth> authProvider;

  private final Provider<FirebaseDatabase> databaseProvider;

  public UserRepository_Factory(Provider<FirebaseAuth> authProvider,
      Provider<FirebaseDatabase> databaseProvider) {
    this.authProvider = authProvider;
    this.databaseProvider = databaseProvider;
  }

  @Override
  public UserRepository get() {
    return newInstance(authProvider.get(), databaseProvider.get());
  }

  public static UserRepository_Factory create(Provider<FirebaseAuth> authProvider,
      Provider<FirebaseDatabase> databaseProvider) {
    return new UserRepository_Factory(authProvider, databaseProvider);
  }

  public static UserRepository newInstance(FirebaseAuth auth, FirebaseDatabase database) {
    return new UserRepository(auth, database);
  }
}
