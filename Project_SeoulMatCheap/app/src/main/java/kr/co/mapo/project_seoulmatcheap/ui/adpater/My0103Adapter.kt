package kr.co.mapo.project_seoulmatcheap.ui.adpater

import android.animation.ValueAnimator
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.data.response.NotifyResponse
import kr.co.mapo.project_seoulmatcheap.databinding.ItemMy0103Binding
import kr.co.mapo.project_seoulmatcheap.ui.activity.MY_01_03

/**
 * @author SANDY
 * @email nnal0256@naver.com
 * @created 2021-05-20
 * @desc
 */
class My0103Adapter (private val list: List<NotifyResponse.Data>) : RecyclerView.Adapter<My0103Adapter.ViewHolderClass>() {

    //Item의 클릭 상태를 저장할 SparseBooleanarray 객체
    private val selectedItems = SparseBooleanArray()
    private var prePosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val binding = ItemMy0103Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderClass(binding)
    }
    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        with(holder) {
            setView(list[position])
            changeVisibility(selectedItems[position])
        }

    }
    override fun getItemCount():Int{
        return list.size
    }

    inner class ViewHolderClass(private val binding : ItemMy0103Binding) : RecyclerView.ViewHolder(binding.root){
        fun setView(item : NotifyResponse.Data) {
            with(binding) {
                notify = item
                holderView = this@ViewHolderClass
                executePendingBindings()
            }
        }
        //클릭시 공지사항 접고 피는 위치 저장
        fun onClick(v: View) {
            if (selectedItems[layoutPosition]) {    //클릭시 닫힌다 -> 이벤트 동작한 포지션의 아이템뷰가 selectedItems에 추가
                selectedItems.delete(layoutPosition)
            } else {    //클릭시 펼쳐진다 -> 이벤트 동작한 포지션의 아이템뷰가 selectedItems에 추가
                selectedItems.delete(prePosition)   //이전 포지션 아이템뷰 삭제
                selectedItems.put(layoutPosition, true)
            }
            if (prePosition != -1) notifyItemChanged(prePosition)
            notifyItemChanged(layoutPosition)
            prePosition = layoutPosition
        }
        //공지사항 접고 펴는 상태와 동작 관리
        fun changeVisibility(isExpanded: Boolean) {
            val height = ViewGroup.LayoutParams.WRAP_CONTENT
            val va =
                if (isExpanded) ValueAnimator.ofInt(0, height) else ValueAnimator.ofInt(height, 0)
            with(va) {
                duration = 600
                addUpdateListener { animation -> val value = animation.animatedValue as Int
                    with(binding) {
                        my0103Content.apply {
                            layoutParams.height = value
                            requestLayout()
                            visibility = if (isExpanded) View.VISIBLE else View.GONE
                        }
                        buttonBefore.visibility = if(isExpanded) View.GONE else View.VISIBLE
                        buttonAfter.visibility = if(isExpanded) View.VISIBLE else View.GONE
                    }
                }
                start()
            }
        }
    }

}