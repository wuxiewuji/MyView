自定义ViewGroup
1.onMeasure
	决定内部View（子View）的宽和高，以及自己的宽和高
2.onLayout
	决定子View的放置位置
3.onTouchEvent
	决定内部View的移动效果

4.自定义属性：允许用户设置菜单离屏幕右侧的边距
	1）.书写xml文件，定义属性
	2）.在布局文件中使用，特别注意xmlns
	3）.在构造方法中（三个参数的构造方法）
9