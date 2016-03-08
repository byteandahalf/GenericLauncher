package com.mojang.minecraftpe;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Build.VERSION;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressLint({"DefaultLocale"})
public class HardwareInformation
{
  private static final CPUInfo cpuInfo = getCPUInfo();
  
  public static String getAndroidVersion()
  {
    return "Android " + Build.VERSION.RELEASE;
  }
  
  public static String getCPUFeatures()
  {
    return cpuInfo.getCPULine("Features");
  }
  
  public static CPUInfo getCPUInfo()
  {
    new StringBuffer();
    HashMap localHashMap = new HashMap();
    int j = 0;
    int k = 0;
    int i = 0;
    if (new File("/proc/cpuinfo").exists()) {
      j = k;
    }
    try
    {
      BufferedReader localBufferedReader = new BufferedReader(new FileReader(new File("/proc/cpuinfo")));
      j = k;
      Pattern localPattern = Pattern.compile("(\\w*)\\s*:\\s([^\\n]*)");
      for (;;)
      {
        j = i;
        Object localObject = localBufferedReader.readLine();
        if (localObject == null) {
          break;
        }
        j = i;
        localObject = localPattern.matcher((CharSequence)localObject);
        j = i;
        if (((Matcher)localObject).find())
        {
          j = i;
          if (((Matcher)localObject).groupCount() == 2)
          {
            j = i;
            if (!localHashMap.containsKey(((Matcher)localObject).group(1)))
            {
              j = i;
              localHashMap.put(((Matcher)localObject).group(1), ((Matcher)localObject).group(2));
            }
            j = i;
            if (((Matcher)localObject).group(1).contentEquals("processor")) {
              i += 1;
            }
          }
        }
      }
      j = i;
      if (localBufferedReader != null)
      {
        j = i;
        localBufferedReader.close();
        j = i;
      }
    }
    catch (Exception localException)
    {
      for (;;)
      {
        localException.printStackTrace();
      }
    }
    return new CPUInfo(localHashMap, j);
  }
  
  public static String getCPUName()
  {
    return cpuInfo.getCPULine("Hardware");
  }
  
  public static String getCPUType()
  {
    int currentapiVersion = android.os.Build.VERSION.SDK_INT;
    if (currentapiVersion < 21){
        return Build.CPU_ABI;
    }   
    else {
        return Build.SUPPORTED_ABIS.toString();
    }
  }
  
  public static String getDeviceModelName()
  {
    String str1 = Build.MANUFACTURER;
    String str2 = Build.MODEL;
    if (str2.startsWith(str1)) {
      return str2.toUpperCase();
    }
    return str1.toUpperCase() + " " + str2;
  }
  
  public static String getLocale()
  {
    return Locale.getDefault().toString();
  }
  
  public static int getNumCores()
  {
    return cpuInfo.getNumberCPUCores();
  }
  
  public static class CPUInfo
  {
    private final Map<String, String> cpuLines;
    private final int numberCPUCores;
    
    public CPUInfo(Map<String, String> paramMap, int paramInt)
    {
      this.cpuLines = paramMap;
      this.numberCPUCores = paramInt;
    }
    
    String getCPULine(String paramString)
    {
      if (this.cpuLines.containsKey(paramString)) {
        return (String)this.cpuLines.get(paramString);
      }
      return "";
    }
    
    int getNumberCPUCores()
    {
      return this.numberCPUCores;
    }
  }
}

