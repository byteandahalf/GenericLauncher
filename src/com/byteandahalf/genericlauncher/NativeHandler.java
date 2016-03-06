package com.byteandahalf.genericlauncher;

import java.lang.reflect.*;
import java.nio.ByteBuffer;

public class NativeHandler {

	/** Page can be read.  */
	public static final int PROT_READ = 0x1;
	/** Page can be executed.  */
	public static final int PROT_WRITE = 0x2;
	/** Page can be executed.  */
	public static final int PROT_EXEC = 0x4;
	/** Page cannot be accessed.  */
	public static final int PROT_NONE = 0x0;

	/** Query parameter for the memory page size, used for sysconf. */
	public static final int _SC_PAGESIZE = 0x0027;

	public static native int mprotect(long addr, long len, int prot);

	/** Get system configuration. Is here because Libcore is available on (some old?) Gingerbread versions */
	public static native long sysconf(int name);


	public static void init() {
		nativeSetupHooks();
	}

	public static ByteBuffer createDirectByteBuffer(long address, long length) throws Exception {
		Constructor cons = Class.forName("java.nio.ReadWriteDirectByteBuffer").getDeclaredConstructor(Integer.TYPE, Integer.TYPE);
		cons.setAccessible(true);
		return (ByteBuffer) cons.newInstance((int) address, (int) length);
	}

	public static native void nativeSetupHooks();

	static {
		//System.loadLibrary("gnustl_shared");
		System.loadLibrary("genericlauncher_tinysubstrate");
		System.loadLibrary("genericlauncher");
	}
}
