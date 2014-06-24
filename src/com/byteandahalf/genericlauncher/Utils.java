package com.byteandahalf.genericlauncher;

import java.io.File;
import java.lang.reflect.Field;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import com.mojang.minecraftpe.MainActivity;

public class Utils {
	protected static Context mContext = null;

	public static void setContext(Context context) {
		mContext = context;
	}
}