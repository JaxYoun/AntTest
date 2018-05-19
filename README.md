# AntTest
测试Ant打jar包
其中包括java类的编译，配置文件，外部jar包引用
build的编写
属性配置
target依赖关系及执行顺序
task的配置

下面详解各节点及属性含义：

project
根节点，代表一个工程 
属性说明： 
name 表示工程名称 
basedir 表示基准目录(值为”.”代表当前目录，即build.xml所在目录，也可采用绝对路径) 
default 表示默认运行的target(当ant命令没有指定target时,会运行default属性中的target)

target
目标节点，代表着一个构建目标 
属性说明： 
name 表示目标名称 
depends 表示依赖的target。像上例中，dist依赖build,build依赖init，也就是说，当ant运行dist这个target时，会根据依赖关系先运行build,build又会先运行init，各个target的执行次序是init->build->dist。即逻辑上的先执行初始化操作，然后进行源代码编译，最后将class文件打成jar包的过程。

property
设置一个或多个属性（可以在其它地方通过${属性名}进行引用） 
属性说明： 
name 表示属性名(区分大小写) 
value 表示属性值 
file 表示要加载的属性文件路径（该文件可包含多个属性，类似jdbc.properties的内容）

下面接着说明各个target中tasks含义

mkdir
创建目录
属性说明：
dir 表示要创建目录

delete
删除目录或文件
属性说明：
dir 表示要删除的目录，该目录下的文件和子目录都会被删除
file 表示要删除的文件

echo
向屏幕或文件输出信息
属性说明：
message 表示要输出的信息
file    表示信息输出到该文件中(如果指定了该属性，信息将不会在屏幕上显示)
level   表示信息级别（未指定该属性时的默认级别为"warning"）

copy
拷贝文件或目录
属性说明：
todir            表示拷贝到哪个目录下
includeemptydirs 是否拷贝空目录
file             要拷贝的单个文件（只针对单个文件的拷贝，上例中没有指定该属性，而是通过内嵌<fileset>来指定多个文件）

javac
编译源文件
属性说明：
srcdir    源文件路径（多个源之间用：分隔，可通过内嵌<src>元素进行设定，如上例）
destdir   存放编译后的class文件路径
includes  包含的文件列表（多个列表用逗号或空格分隔，列表可用通配符指定）。该属性省略时，所有的.java文件被包含进去
excludes  排除的文件列表（多个列表用逗号或空格分隔，列表可用通配符指定）。
debug     编译时是否显示调试信息
includeantruntime 是否包含ant库路径
classpath 依赖库路径（上例中通过内嵌<classpath>元素进行了设定）

jar
将class文件打成jar包
属性说明：
destfile  将创建的jar包位置
basedir   将被打成jar包的目录（或通过内嵌的<fileset>元素进行设定）
includes  包含的文件列表（多个列表用逗号或空格分隔，列表可用通配符指定）。该属性省略时，所有的文件被包含进去
excludes  排除的文件列表（多个列表用逗号或空格分隔，列表可用通配符指定）。
可内嵌<manifest>元素，通过设定Main-Class属性来设定jar包的入口类（如上例）

Resource Collections
ant中经常需要指定资源集合，主要通过下面元素指定 
fileset

属性说明：
dir    文件集合的根目录（该目录下的文件都会被包含）
file   快捷指定单个文件
includes  包含的文件列表（多个列表用逗号或空格分隔，列表可用通配符指定）。该属性省略时，所有的文件被包含进去
excludes  排除的文件列表（多个列表用逗号或空格分隔，列表可用通配符指定）。
casesensitive 指定包含或排除模式匹配时是否区分大小写，默认区分
可内嵌<include>、<exclude>、<patternset>、<filename>等元素

patternset

模式集合
属性说明：
id 唯一标识（其它<patternset>元素通过refid指向该模式）
可内嵌<include>、<exclude>、<includesfile>、<excludesfile>等元素

include 
exclude

指定单个模式(包含或排除)
属性说明：
name 要包含或排除的模式

includesfile 
excludesfile

指定模式文件
属性说明：
name 模式文件名(文件内容为包含或排除模式)

Path-like Structures
在通过ant运行java类，或执行编译时，都需要指定classpath，可通过下面三个元素来指定引用的jar包或class文件

##classpath

类路径，内嵌在<javac>,<java>等元素中，表示依赖库路径
属性说明：
refid    指向该id对应的元素
location 单个文件或目录（目录可以是相对路径或绝对路径，相对路径是指相对于工程的基准路径）
path     多个location的集合（各location以冒号或分号进行分隔），一般用来引用预定义的路径，如引用环境变量${classpath}
其它说明：
可内嵌<fileset>、<pathelement>等元素来指定jar文件或目录

##path

类路径集合，被<classpath>或其它<path>引用。
path用法类似<classpath>元素，但是<path>跟<target>同级，而classpath是内嵌在<java>或<javac>元素中。
##pathelement

单个类路径，内嵌在<classpath>或<path>中
属性说明
location 单个文件或目录（目录可以是相对路径或绝对路径，相对路径是指相对于工程的基准路径）
path     多个location的集合（各location以冒号或分号进行分隔），一般用来引用预定义的路径，如引用环境变量${class