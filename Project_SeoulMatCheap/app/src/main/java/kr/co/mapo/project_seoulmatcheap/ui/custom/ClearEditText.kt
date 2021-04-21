package kr.co.mapo.project_seoulmatcheap.ui.custom

import android.R.attr.visible
import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import kr.co.mapo.project_seoulmatcheap.R


/**
 * @author SANDY
 * @email nnal0256@naver.com
 * @created 2021-04-21
 * @desc
 */
class ClearEditText :
    AppCompatEditText, TextWatcher, View.OnTouchListener, View.OnFocusChangeListener{

    private lateinit var clearDrawable : Drawable
    private var onTouchListener: OnTouchListener? = null

    constructor(context: Context) : super(context) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        //EditText에 'X'버튼 추가
        clearDrawable = DrawableCompat.wrap(ContextCompat.getDrawable(context, R.drawable.ic_round_clear_24)!!)
        clearDrawable.setBounds(0,0, clearDrawable.intrinsicWidth, clearDrawable.intrinsicHeight)
        setClearIconVisible(false)

        //텍스트 길이에 따라 X버튼 보이기/없애기
        addTextChangedListener(this)
        //X버튼이 눌리는경우 텍스트 초기화
        super.setOnTouchListener(this)
        //EditText에 포커스가 있을때에만 X버튼을 보이기
        super.setOnFocusChangeListener(this)
    }

    // 'X'버튼 보이기 설정
    private fun setClearIconVisible(b: Boolean) {
        clearDrawable.setVisible(b, false)
        //editText의 오른쪽에 버튼을 위치
        setCompoundDrawables(null, null, if (b) clearDrawable else null, null)
    }

    //텍스트 길이에 따라 X버튼 보이기/없애기
    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
         if (isFocused) {
             if (text != null) {
                 setClearIconVisible(text.isNotEmpty())
             }
         }
    }
    override fun afterTextChanged(s: Editable?) {
    }
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    //X버튼이 눌리는경우 텍스트 초기화
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        val x = event?.x?.toInt()
        if (x != null) {
            if((clearDrawable.isVisible) && (x > (width-paddingRight-clearDrawable.intrinsicWidth))) {
                if(event.action == MotionEvent.ACTION_UP) {
                    error = null
                    text = null
                }
                return true
            }
        }
        return if(onTouchListener != null) {
            onTouchListener!!.onTouch(v, event)
        } else false
    }
    override fun setOnTouchListener(l: OnTouchListener?) {
        super.setOnTouchListener(l)
        this.onTouchListener = l
    }

    //EditText에 포커스가 있을때에만 X버튼을 보이기
    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        if(hasFocus) {
            setClearIconVisible(text!!.isNotEmpty())
        } else {
            setClearIconVisible(false)
        }
    }

}