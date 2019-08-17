package app.skynight.musicplayer.view

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.content.ContextCompat
import app.skynight.musicplayer.MainApplication
import app.skynight.musicplayer.R
import app.skynight.musicplayer.broadcast.BroadcastBase.Companion.CLIENT_BROADCAST_LAST
import app.skynight.musicplayer.broadcast.BroadcastBase.Companion.CLIENT_BROADCAST_NEXT
import app.skynight.musicplayer.util.UnitUtil.Companion.getPx
import app.skynight.musicplayer.broadcast.BroadcastBase.Companion.CLIENT_BROADCAST_ONPAUSE
import app.skynight.musicplayer.broadcast.BroadcastBase.Companion.CLIENT_BROADCAST_ONSTART
import app.skynight.musicplayer.broadcast.BroadcastBase.Companion.CLIENT_BROADCAST_ONSTOP

/**
 * @FILE:   BottomPlayerView
 * @AUTHOR: 1552980358
 * @DATE:   19 Jul 2019
 * @TIME:   8:42 AM
 **/

@Suppress("PropertyName")
class BottomPlayerView : LinearLayout {

     lateinit var imageView_album: MusicAlbumRoundedImageView

     lateinit var textView_title: StyledTextView
     lateinit var textView_subTitle: StyledTextView

     lateinit var checkBox_controller: AppCompatCheckBox

     lateinit var linearLayout_Root: LinearLayout

     private fun createView() {
        orientation = VERTICAL
        addView(
            View(context).apply {
                background = ContextCompat.getDrawable(
                    context,
                    if (MainApplication.customize) R.color.white else R.color.black
                )
            },
            LayoutParams(
                MATCH_PARENT,
                resources.getDimensionPixelSize(R.dimen.bottomPlayerView_divider)
            )
        )
        addView(
            LinearLayout(context).apply {
                orientation = HORIZONTAL
                gravity = Gravity.CENTER
                isClickable = true
                isFocusable = true
                background = ContextCompat.getDrawable(context, R.drawable.ripple_effect)
                linearLayout_Root = this

                addView(LinearLayout(context).apply {
                    orientation = HORIZONTAL

                    addView(MusicAlbumRoundedImageView(context).apply {
                        imageView_album = this
                        //setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_player_play))
                    }, LayoutParams(
                        resources.getDimensionPixelSize(R.dimen.bottomPlayerView_size),
                        resources.getDimensionPixelSize(R.dimen.bottomPlayerView_size)
                    ).apply {
                        setMargins(
                            resources.getDimensionPixelSize(R.dimen.bottomPlayerView_marginLeft),
                            0,
                            0,
                            0
                        )
                        gravity = Gravity.CENTER_VERTICAL
                    })

                    addView(LinearLayout(context).apply {
                        orientation = VERTICAL

                        addView(StyledTextView(context).apply {
                            textView_title = this
                            textSize = resources.getDimension(R.dimen.bottomPlayerView_title_size)
                            //text = "title"
                            setSingleLine()
                            setHorizontallyScrolling(true)
                            marqueeRepeatLimit = -1
                            ellipsize = TextUtils.TruncateAt.MARQUEE
                            isSelected = true
                        }, LayoutParams(MATCH_PARENT, WRAP_CONTENT))

                        addView(StyledTextView(context).apply {
                            textView_subTitle = this
                            //text = "subtitle"
                            textSize = resources.getDimension(R.dimen.bottomPlayerView_subTitle_size)
                            setSingleLine()
                            ellipsize = TextUtils.TruncateAt.END
                        }, LayoutParams(MATCH_PARENT, WRAP_CONTENT))

                    }, LayoutParams(MATCH_PARENT, MATCH_PARENT).apply {
                        setMargins(
                            resources.getDimensionPixelSize(R.dimen.bottomPlayerView_text_margin),
                            resources.getDimensionPixelSize(R.dimen.bottomPlayerView_text_margin),
                            0,//resources.getDimensionPixelSize(R.dimen.bottomPlayerView_text_margin),
                            resources.getDimensionPixelSize(R.dimen.bottomPlayerView_text_margin)
                        )
                    })
                }, LayoutParams(0, MATCH_PARENT).apply {
                    weight = 1f
                })

                addView(LinearLayout(context).apply {

                    orientation = HORIZONTAL
                    gravity = Gravity.CENTER_VERTICAL

                    addView(AppCompatImageButton(context).apply {
                        //imageButton_last = this
                        background =
                            ContextCompat.getDrawable(context, R.drawable.ic_playerbar_last)
                        isClickable = true
                        isFocusable = true
                        setOnClickListener {
                            context.sendBroadcast(Intent(CLIENT_BROADCAST_LAST))
                        }
                    }, LayoutParams(getPx(20), getPx(20)))

                    addView(AppCompatCheckBox(context).apply {
                        checkBox_controller = this
                        buttonDrawable = null
                        background =
                            ContextCompat.getDrawable(context, R.drawable.ic_playerbar_ctrl)
                        setOnClickListener {
                            context.sendBroadcast(Intent(if (isChecked) CLIENT_BROADCAST_ONSTART else CLIENT_BROADCAST_ONPAUSE))
                        }
                        setOnLongClickListener {
                            context.sendBroadcast(Intent(CLIENT_BROADCAST_ONSTOP))
                            true
                        }
                    }, LayoutParams(getPx(35), getPx(35)))
                    addView(AppCompatImageButton(context).apply {
                        //imageButton_next = this
                        background =
                            ContextCompat.getDrawable(context, R.drawable.ic_playerbar_next)
                        isClickable = true
                        isFocusable = true
                        setOnClickListener {
                            context.sendBroadcast(Intent(CLIENT_BROADCAST_NEXT))
                        }
                    }, LayoutParams(getPx(20), getPx(20)))
                }, LayoutParams(WRAP_CONTENT, MATCH_PARENT))
            },
            LayoutParams(
                MATCH_PARENT,
                resources.getDimensionPixelSize(R.dimen.bottomPlayerView_object)
            )
        )
    }

    constructor(context: Context) : this(context, null)
    @Suppress("UNUSED_PARAMETER")
    constructor(context: Context, attributeSet: AttributeSet?) : super(context) {
        background = if (MainApplication.customize) ContextCompat.getDrawable(
            context, R.color.player_widget_bg
        ) else ContextCompat.getDrawable(context, R.color.colorPrimaryDark)
        createView()
    }

    fun setRootOnClickListener(l: OnClickListener) {
        linearLayout_Root.setOnClickListener(l)
    }
}