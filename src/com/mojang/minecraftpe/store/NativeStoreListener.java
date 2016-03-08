package com.mojang.minecraftpe.store;

public class NativeStoreListener
  implements StoreListener
{
  long mStoreListener;
  
  public NativeStoreListener(long paramLong)
  {
    this.mStoreListener = paramLong;
  }
  
  public native void onPurchaseCanceled(long paramLong, String paramString);
  
  public void onPurchaseCanceled(String paramString)
  {
    onPurchaseCanceled(this.mStoreListener, paramString);
  }
  
  public native void onPurchaseFailed(long paramLong, String paramString);
  
  public void onPurchaseFailed(String paramString)
  {
    onPurchaseFailed(this.mStoreListener, paramString);
  }
  
  public native void onPurchaseSuccessful(long paramLong, String paramString);
  
  public void onPurchaseSuccessful(String paramString)
  {
    onPurchaseSuccessful(this.mStoreListener, paramString);
  }
  
  public void onQueryProductsFail()
  {
    onQueryProductsFail(this.mStoreListener);
  }
  
  public native void onQueryProductsFail(long paramLong);
  
  public native void onQueryProductsSuccess(long paramLong, Product[] paramArrayOfProduct);
  
  public void onQueryProductsSuccess(Product[] paramArrayOfProduct)
  {
    onQueryProductsSuccess(this.mStoreListener, paramArrayOfProduct);
  }
  
  public void onQueryPurchasesFail()
  {
    onQueryPurchasesFail(this.mStoreListener);
  }
  
  public native void onQueryPurchasesFail(long paramLong);
  
  public native void onQueryPurchasesSuccess(long paramLong, Purchase[] paramArrayOfPurchase);
  
  public void onQueryPurchasesSuccess(Purchase[] paramArrayOfPurchase)
  {
    onQueryPurchasesSuccess(this.mStoreListener, paramArrayOfPurchase);
  }
  
  public native void onStoreInitialized(long paramLong, boolean paramBoolean);
  
  public void onStoreInitialized(boolean paramBoolean)
  {
    onStoreInitialized(this.mStoreListener, paramBoolean);
  }
}

