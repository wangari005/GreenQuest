package com.greenquest.app;

import com.greenquest.app.data.local.DatabaseInitializer;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class GreenQuestApp_MembersInjector implements MembersInjector<GreenQuestApp> {
  private final Provider<DatabaseInitializer> databaseInitializerProvider;

  public GreenQuestApp_MembersInjector(Provider<DatabaseInitializer> databaseInitializerProvider) {
    this.databaseInitializerProvider = databaseInitializerProvider;
  }

  public static MembersInjector<GreenQuestApp> create(
      Provider<DatabaseInitializer> databaseInitializerProvider) {
    return new GreenQuestApp_MembersInjector(databaseInitializerProvider);
  }

  @Override
  public void injectMembers(GreenQuestApp instance) {
    injectDatabaseInitializer(instance, databaseInitializerProvider.get());
  }

  @InjectedFieldSignature("com.greenquest.app.GreenQuestApp.databaseInitializer")
  public static void injectDatabaseInitializer(GreenQuestApp instance,
      DatabaseInitializer databaseInitializer) {
    instance.databaseInitializer = databaseInitializer;
  }
}
