package com.linji.cabinet_file.widget


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.linji.cabinetutil.R
import com.linji.cabinetutil.bean.XCDropBean
import com.linji.cabinetutil.util.ScreenUtil

@SuppressLint("NewApi")
/**
 * 下拉列表框控件
 * @author caizhiming
 */
class XCDropDownListView(context: Context, attrs: AttributeSet?, defStyle: Int) :
    LinearLayout(context, attrs, defStyle) {

    private var editText: TextView? = null
    private var imageView: ImageView? = null
    private var popupWindow: PopupWindow? = null
    private var dataList: List<XCDropBean> = ArrayList()
    private var dropTextSize = 12f
    private lateinit var view: View

    val selectItem: String
        get() = editText!!.text.toString()
    var listWidth: Float = 0f

    constructor(context: Context) : this(context, null) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {
        initView()
    }

    init {
        initView()
    }

    fun initView() {
        val layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        view = layoutInflater.inflate(R.layout.dropdownlist_view, this, true)

        editText = findViewById(R.id.text)
        imageView = findViewById(R.id.btn)
        this.setOnClickListener {
            if (popupWindow == null) {
                showPopWindow()
            } else {
                closePopWindow()
            }
        }
    }

    fun setViewBackgroundColor(color:Int){
        view.setBackgroundColor(color)
    }
    fun setTextViewBackground(color: Int){
        editText!!.setBackgroundColor(color)
    }
    fun setTextColor(color: Int){
        editText!!.setTextColor(color)
    }
    fun setTextSize(size:Float){
        editText!!.textSize = size
    }
    fun setDropTextSize(size:Float){
        dropTextSize = size
    }
    fun setTextHintColor(color: Int){
        editText!!.setHintTextColor(color)
    }

    fun setImageDrawable(drawable: Drawable){
        imageView!!.setImageDrawable(drawable)
    }
    fun setImageResource(resId: Int){
        imageView!!.setImageResource(resId)
    }
    /**
     * 打开下拉列表弹窗
     */
    private fun showPopWindow() {
        imageView!!.rotation = 180f
        // 加载popupWindow的布局文件
        val layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val contentView = layoutInflater.inflate(R.layout.dropdownlist_popupwindow, null, false)
        val listView = contentView.findViewById<RecyclerView>(R.id.listView)
        listView.layoutManager = LinearLayoutManager(context)
        listView.adapter = XCDropDownListAdapter(dataList)
        popupWindow = PopupWindow(
            contentView,
            ScreenUtil.dip2px(context, layoutParams.width.toFloat()),
            ScreenUtil.dip2px(context, 200f)
        )
        popupWindow!!.width = editText!!.measuredWidth
        popupWindow!!.setBackgroundDrawable(resources.getDrawable(R.color.transparent))
        popupWindow!!.setOnDismissListener {
            closePopWindow()
        }
        popupWindow!!.isOutsideTouchable = true
        popupWindow!!.showAsDropDown(view)
    }

    /**
     * 关闭下拉列表弹窗
     */
    private fun closePopWindow() {
        imageView!!.rotation = 0f
        popupWindow!!.dismiss()
        popupWindow = null
    }

    /**
     * 设置数据
     *
     * @param list
     */
    fun setItemsData(list: List<XCDropBean>) {
        dataList = list
        editText!!.text = list[0].item
    }

    internal inner class XCDropDownListAdapter(data: List<XCDropBean>?) :
        BaseQuickAdapter<XCDropBean, BaseViewHolder>(R.layout.dropdown_list_item, data) {

        override fun convert(helper: BaseViewHolder, item: XCDropBean) {
            helper.setText(R.id.tv, item.item)
            val text = helper.getView<TextView>(R.id.tv)
            text.textSize =dropTextSize

            helper.getView<View>(R.id.layout_container).setOnClickListener { v ->
                editText!!.text = item.item
                if (onXCItemOnClickListener != null){
                    onXCItemOnClickListener!!.onItemClick(item)
                }
                closePopWindow()
            }
        }
    }

    interface OnXCItemOnClickListener{
        fun onItemClick(item:XCDropBean)
    }

    private var onXCItemOnClickListener:OnXCItemOnClickListener? = null

    public fun setOnXcItemClickListener(onXCItemOnClickListener:OnXCItemOnClickListener){
        this.onXCItemOnClickListener = onXCItemOnClickListener
    }
}
