package com.greenquest.app.di;

import com.greenquest.app.data.local.AppDatabase;
import com.greenquest.app.data.local.dao.ActionDao;
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
public final class AppModule_ProvideActionDaoFactory implements Factory<ActionDao> {
  private final Provider<AppDatabase> dbProvider;

  public AppModule_ProvideActionDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public ActionDao get() {
    return provideActionDao(dbProvider.get());
  }

  public static AppModule_ProvideActionDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new AppModule_ProvideActionDaoFactory(dbProvider);
  }

  public static ActionDao provideActionDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideActionDao(db));
  }
}
