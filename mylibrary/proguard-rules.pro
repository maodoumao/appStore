# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


# ----------------------------------------------------------------------------
-optimizationpasses 5 #指定压缩级别
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/* #混淆时采用的算法
-verbose #打印混淆的详细信息
-dontoptimize #关闭优化
-keepattributes Signature  # 避免混淆泛型, 这在JSON实体映射时非常重要
-ignorewarnings # 屏蔽警告
-keepattributes SourceFile,LineNumberTable # 抛出异常时保留代码行号
#混淆时不使用大小写混合，混淆后的类名为小写(大小写混淆容易导致class文件相互覆盖）
-dontusemixedcaseclassnames
-obfuscationdictionary proguardbuild/pro_package.txt
#未混淆的类和成员
-printseeds proguardbuild/print_seeds.txt
#列出从 apk 中删除的代码
-printusage proguardbuild/print_unused.txt
#混淆前后的映射，生成映射文件
-printmapping proguardbuild/print_mapping.txt
# 指定一个文本文件，其中所有有效字词都用作混淆字段和方法名称。
# 默认情况下，诸如“a”，“b”等短名称用作混淆名称。
# 使用模糊字典，您可以指定保留关键字的列表，或具有外来字符的标识符，
# 例如： 忽略空格，标点符号，重复字和＃符号后的注释。
# 注意，模糊字典几乎不改善混淆。 有些编译器可以自动替换它们，并且通过使用更简单的名称再次混淆，可以很简单地撤消该效果。
# 最有用的是指定类文件中通常已经存在的字符串（例如'Code'），从而减少类文件的大小。 仅适用于混淆处理。
-obfuscationdictionary proguardbuild/pro_package.txt

# 指定一个文本文件，其中所有有效词都用作混淆类名。 与-obfuscationdictionary类似。 仅适用于混淆处理。
-classobfuscationdictionary proguardbuild/pro_class.txt

# 指定一个文本文件，其中所有有效词都用作混淆包名称。与-obfuscationdictionary类似。 仅适用于混淆处理。
-packageobfuscationdictionary proguardbuild/pro_func.txt