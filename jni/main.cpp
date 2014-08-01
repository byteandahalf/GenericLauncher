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
typedef Player LocalPlayer;

bool HEY_FUNCTIONS_HOOKED_ALREADY_BRO = false;


JavaVM* javaVM;
jclass nativehandler_class;

static void (*hk_Minecraft_setLevel_real)(Minecraft*, Level*, std::string const&, LocalPlayer*);
static void (*hk_Minecraft_leaveGame_real)(Minecraft*, int);
static void (*hk_Common_getGameVersionString_real)();

void MSHookFunction(void *symbol, void *hook, void **real);


static Minecraft* minecraft_inst;
static LocalPlayer* localplayer_inst;
static Level* level_inst = NULL;


void hk_Minecraft_setLevel_hook(Minecraft* minecraft, Level* level, std::string const& levelName, LocalPlayer* player) {
	// Make it do stuff here:
	level_inst = level;
	__android_log_print(ANDROID_LOG_INFO, "GenericLauncher", "Into the world!");
	hk_Minecraft_setLevel_real(minecraft, level, levelName, player); // Continue its normal process
}

void hk_Minecraft_leaveGame_hook(Minecraft* minecraft, int unk) {
	// Make it do stuff here:
	level_inst = NULL;
	__android_log_print(ANDROID_LOG_INFO, "GenericLauncher", "Out of the world!");
	hk_Minecraft_leaveGame_real(minecraft, unk); // Continue its normal process
}

std::string hk_Common_getGameVersionString_hook() {
	// If the world hasn't loaded yet, print the usual version number(for the title screen)
	// else, return a blank string so the version watermark is absent in game.
	if(level_inst == NULL) {
		return "v0.9.0 alpha build 7";
	} else {
		return " ";
	}
}

JNIEXPORT jint JNICALL Java_net_zhuoweizhang_pokerface_PokerFace_mprotect
  (JNIEnv *env, jclass clazz, jlong addr, jlong len, jint prot) {
	return mprotect((void *)(uintptr_t) addr, len, prot);
}

JNIEXPORT jlong JNICALL Java_net_zhuoweizhang_pokerface_PokerFace_sysconf
  (JNIEnv *env, jclass clazz, jint name) {
	long result = sysconf(name);
	return result;
}

JNIEXPORT void JNICALL Java_com_byteandahalf_genericlauncher_NativeHandler_nativeSetupHooks
  (JNIEnv *env, jclass clazz) {

  	// Let's not call every hook 3.000.000 times, OK?
  	if(HEY_FUNCTIONS_HOOKED_ALREADY_BRO == true) return;

  	void *handle;
  	handle = dlopen("libminecraftpe.so", RTLD_LAZY);
  	soinfo2* weakhandle = (soinfo2*) dlopen("libminecraftpe.so", RTLD_LAZY);


  	void* hk_Minecraft_setLevel = dlsym(handle, "_ZN9Minecraft8setLevelEP5LevelRKSsP11LocalPlayer");
  	MSHookFunction(hk_Minecraft_setLevel, (void*) &hk_Minecraft_setLevel_hook, (void**) &hk_Minecraft_setLevel_real);

  	void* hk_Minecraft_leaveGame = dlsym(handle, "_ZN9Minecraft9leaveGameEbb");
  	MSHookFunction(hk_Minecraft_leaveGame, (void*) &hk_Minecraft_leaveGame_hook, (void**) &hk_Minecraft_leaveGame_real);

    	void* hk_Common_getGameVersionString = dlsym(handle, "_ZN6Common20getGameVersionStringEv");
    	MSHookFunction(hk_Common_getGameVersionString, (void*) &hk_Common_getGameVersionString_hook, (void**) &hk_Common_getGameVersionString_real);



  	dlerror();

  	jclass clz = env->FindClass("com/byteandahalf/genericlauncher/NativeHandler");
  	nativehandler_class = (jclass) env->NewGlobalRef(clz); // No idea why I have to cast to a jclass
  	
  	HEY_FUNCTIONS_HOOKED_ALREADY_BRO = true;

  	const char* myerror = dlerror();
	if (myerror != NULL) {
		__android_log_print(ANDROID_LOG_ERROR, "HALP", "Hooking errors: %s\n", myerror);
	}
}
