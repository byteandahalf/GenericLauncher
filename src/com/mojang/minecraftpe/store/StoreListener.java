package com.mojang.minecraftpe.store;

public abstract interface StoreListener
{
  public abstract void onPurchaseCanceled(String paramString);
  
  public abstract void onPurchaseFailed(String paramString);
  
  public abstract void onPurchaseSuccessful(String paramString);
  
  public abstract void onQueryProductsFail();
  
  public abstract void onQueryProductsSuccess(Product[] paramArrayOfProduct);
  
  public abstract void onQueryPurchasesFail();
  
  public abstract void onQueryPurchasesSuccess(Purchase[] paramArrayOfPurchase);
  
  public abstract void onStoreInitialized(boolean paramBoolean);
}


/* Location:              /home/aurelien/C/mcpemod/decompile/classes-dex2jar.jar!/com/mojang/minecraftpe/store/StoreListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */