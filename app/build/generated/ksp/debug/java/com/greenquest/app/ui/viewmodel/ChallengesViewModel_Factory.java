package com.greenquest.app.ui.viewmodel;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class ChallengesViewModel_Factory implements Factory<ChallengesViewModel> {
  @Override
  public ChallengesViewModel get() {
    return newInstance();
  }

  public static ChallengesViewModel_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static ChallengesViewModel newInstance() {
    return new ChallengesViewModel();
  }

  private static final class InstanceHolder {
    private static final ChallengesViewModel_Factory INSTANCE = new ChallengesViewModel_Factory();
  }
}
