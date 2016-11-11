#include "{TEST_OBJECT_NAME}.h"
#include "CTestObjectHandle.h"
JNIEXPORT void JNICALL {JNI_FUNCTION_NAME}(JNIEnv *env, jobject jobj, jdoubleArray args)
{
	initialize_and_call(env, jobj, args);
}

{INSTRUMENTED_CODE}

{PERFORM_CALL_CODE}
