package com.mojang.minecraftpe.store;

import android.util.Log;

public class Store
{
  private StoreListener listener;
  
  public Store(StoreListener paramStoreListener)
  {
    this.listener = paramStoreListener;
  }
  
  public void destructor()
  {
    Log.i("GenericLauncher","Store: Destructor");
  }
  
  public String getStoreId()
  {
    Log.i("GenericLauncher","Store: Get store ID");
    return "Placeholder store ID";
  }
  
  public void purchase(String paramString)
  {
    Log.i("GenericLauncher","Store: Purchase " + paramString);
  }
  
  public void queryProducts(String[] paramArrayOfString)
  {
    Log.i("GenericLauncher","Store: Query products");
  }
  
  public void queryPurchases()
  {
    Log.i("GenericLauncher","Store: Query purchases");
  }
}

