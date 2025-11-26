package com.greenquest.app.di;

import com.greenquest.app.data.local.dao.ActionDao;
import com.greenquest.app.data.repo.ActionRepository;
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
public final class AppModule_ProvideActionRepositoryFactory implements Factory<ActionRepository> {
  private final Provider<ActionDao> daoProvider;

  public AppModule_ProvideActionRepositoryFactory(Provider<ActionDao> daoProvider) {
    this.daoProvider = daoProvider;
  }

  @Override
  public ActionRepository get() {
    return provideActionRepository(daoProvider.get());
  }

  public static AppModule_ProvideActionRepositoryFactory create(Provider<ActionDao> daoProvider) {
    return new AppModule_ProvideActionRepositoryFactory(daoProvider);
  }

  public static ActionRepository provideActionRepository(ActionDao dao) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideActionRepository(dao));
  }
}
