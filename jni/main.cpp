#include <dlfcn.h>
#include <stdio.h>
#include <stdint.h>
#include <stdlib.h>
#include <android/log.h>
#include <jni.h>
#include <malloc.h>
#include <unistd.h>
#include <string>
#include <sys/mman.h>
#include <substrate.h>
#include <dobby_public.h>
#include <dl_internal.h>

#include <main.h>

/* 
Put some typedefs and structs here
*/
typedef void Level;
typedef void Player;
typedef void Minecraft;
typedef void MinecraftClient;
typedef Player LocalPlayer;

bool HEY_FUNCTIONS_HOOKED_ALREADY_BRO = false;

JavaVM* javaVM;
jclass nativehandler_class;

static std::string (*hk_Common_getGameVersionString_real)();
static void (*hk_MinecraftClient_init_real)(MinecraftClient*);
static bool (*hk_Minecraft_isModded_real)(Minecraft*);

void MSHookFunction(void* symbol, void* hook, void** real);

static Minecraft* minecraft_inst;
static MinecraftClient* mcclient_inst;
static LocalPlayer* localplayer_inst;
static Level* level_inst = NULL;

std::string hk_Common_getGameVersionString_hook() {
	// If the world hasn't loaded yet, print the usual version number(for the title screen)
	// else, return a blank string so the version watermark is absent in game.
	if (level_inst == NULL) { // Since I deleted the hook that gave Level a definition, Level will be always null
		return "v0.14.0";
	} else {
		return " ";
	}
}

void hk_MinecraftClient_init_hook(MinecraftClient* client) {
	__android_log_print(ANDROID_LOG_INFO, "GenericLauncher", "MinecraftClient::init");
	mcclient_inst = client;
	hk_MinecraftClient_init_real(client);	
}

bool hk_Minecraft_isModded_hook(Minecraft* mc) {
	return true;
}

JNIEXPORT jint JNICALL Java_net_zhuoweizhang_pokerface_PokerFace_mprotect(JNIEnv* env, jclass clazz, jlong addr, jlong len, jint prot) {
	return mprotect((void *)(uintptr_t) addr, len, prot);
}

JNIEXPORT jlong JNICALL Java_net_zhuoweizhang_pokerface_PokerFace_sysconf(JNIEnv* env, jclass clazz, jint name) {
	long result = sysconf(name);
	return result;
}

JNIEXPORT void JNICALL Java_com_byteandahalf_genericlauncher_NativeHandler_nativeSetupHooks(JNIEnv* env, jclass clazz) {
	__android_log_print(ANDROID_LOG_INFO, "GenericLauncher", "SetupHook");
  	// Let's not call every hook 3.000.000 times, OK?
  	if(HEY_FUNCTIONS_HOOKED_ALREADY_BRO == true) return;

  	void *handle;
  	handle = dlopen("libminecraftpe.so", RTLD_LAZY);
  	soinfo2* weakhandle = (soinfo2*) dlopen("libminecraftpe.so", RTLD_LAZY);
  	
	void* hk_Common_getGameVersionString = dlsym(handle, "_ZN6Common20getGameVersionStringEv");
	MSHookFunction(hk_Common_getGameVersionString, (void*) &hk_Common_getGameVersionString_hook, (void**) &hk_Common_getGameVersionString_real);

	void* hk_MinecraftClient_init = dlsym(handle, "_ZN15MinecraftClient4initEv");
	MSHookFunction(hk_MinecraftClient_init, (void*) &hk_MinecraftClient_init_hook, (void**) &hk_MinecraftClient_init_real);
	
	void* hk_Minecraft_isModded = dlsym(handle, "_ZN9Minecraft8isModdedEv");
	MSHookFunction(hk_Minecraft_isModded, (void*) &hk_Minecraft_isModded_hook, (void**) &hk_Minecraft_isModded_real);

  	dlerror();

  	jclass clz = env->FindClass("com/byteandahalf/genericlauncher/NativeHandler");
  	nativehandler_class = (jclass) env->NewGlobalRef(clz); // No idea why I have to cast to a jclass
  	
  	HEY_FUNCTIONS_HOOKED_ALREADY_BRO = true;

  	const char* myerror = dlerror();
	if (myerror != NULL) {
		__android_log_print(ANDROID_LOG_ERROR, "HALP", "Hooking errors: %s\n", myerror);
	}
}
