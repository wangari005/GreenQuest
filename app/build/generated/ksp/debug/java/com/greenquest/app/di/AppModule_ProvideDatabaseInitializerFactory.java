package com.greenquest.app.di;

import com.google.firebase.database.FirebaseDatabase;
import com.greenquest.app.data.local.DatabaseInitializer;
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
public final class AppModule_ProvideDatabaseInitializerFactory implements Factory<DatabaseInitializer> {
  private final Provider<FirebaseDatabase> databaseProvider;

  public AppModule_ProvideDatabaseInitializerFactory(Provider<FirebaseDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public DatabaseInitializer get() {
    return provideDatabaseInitializer(databaseProvider.get());
  }

  public static AppModule_ProvideDatabaseInitializerFactory create(
      Provider<FirebaseDatabase> databaseProvider) {
    return new AppModule_ProvideDatabaseInitializerFactory(databaseProvider);
  }

  public static DatabaseInitializer provideDatabaseInitializer(FirebaseDatabase database) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideDatabaseInitializer(database));
  }
}
