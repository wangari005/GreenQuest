package com.greenquest.app.data.repository;

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
public final class ActionRepository_Factory implements Factory<ActionRepository> {
  private final Provider<FirebaseDatabase> databaseProvider;

  public ActionRepository_Factory(Provider<FirebaseDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public ActionRepository get() {
    return newInstance(databaseProvider.get());
  }

  public static ActionRepository_Factory create(Provider<FirebaseDatabase> databaseProvider) {
    return new ActionRepository_Factory(databaseProvider);
  }

  public static ActionRepository newInstance(FirebaseDatabase database) {
    return new ActionRepository(database);
  }
}
