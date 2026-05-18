/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.audio;

import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.view.Display;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This class provides the API for accessing audio attributes functionality
 *
 * @since 11
 */
public interface IAudioAttributes {

    /**
     * definition for get Audio Usage {@link #getAudioAttributesUsage(String)}<br>
     * {@link #USAGE_MD_NAVIGATION_HINT}<br>
     */
    @StringDef({
            Usage.USAGE_MD_NAVIGATION_HINT
    })
    @Retention(RetentionPolicy.SOURCE)
    @interface Usage {

        /**
         * Navigation hint<br>
         * 导航转向提示音(叮咚音)
         *
         * @since 11
         */
        String USAGE_MD_NAVIGATION_HINT = "NAVIGATION_HINT";

        /**
         * Sound recording
         * 音频录制
         *
         * @since 16
         */
        String USAGE_ASSISTANT_RECORD = "ASSISTANT_RECORD";
    }

    /**
     * get audio focus request System Usage by Usage Metadata {@link Usage}<br>
     * 通过{@link Usage}获取音频焦点请求 Usage 类型
     *
     * @param usage Usage Metadata, such as {@link Usage#USAGE_MD_NAVIGATION_HINT}
     * @return System Usage for audio focus request {@link android.media.AudioAttributes.Builder#setUsage(int)}
     * @see android.media.AudioManager#requestAudioFocus(AudioFocusRequest)
     * @see android.media.AudioFocusRequest.Builder#setAudioAttributes(AudioAttributes)
     * @see android.media.AudioAttributes.Builder#setUsage(int)
     * @since 11
     */
    int getAudioAttributesUsage(@Usage String usage);


    /**
     * get audio focus request System Usage by Usage Metadata {@link Usage} & {@link Display}<br>
     * 通过{@link Usage}获取音频焦点请求 Usage 类型
     *
     * @param usage   Usage Metadata, such as {@link Usage#USAGE_MD_NAVIGATION_HINT}
     * @param display Which {@link Display} hold & use the Audio Usage, can be get with <b>android.content.Context#getDisplay()</b>
     *                , null same with {@link #getAudioAttributesUsage(String)}
     * @return System Usage for audio focus request {@link android.media.AudioAttributes.Builder#setUsage(int)}
     * @see android.media.AudioManager#requestAudioFocus(AudioFocusRequest)
     * @see android.media.AudioFocusRequest.Builder#setAudioAttributes(AudioAttributes)
     * @see android.media.AudioAttributes.Builder#setUsage(int)
     * @since 11
     */
    int getAudioAttributesUsage(@Usage String usage, Display display);

    /**
     * definition for get Audio Content type {@link #getAudioAttributesContentType(String)}<br>
     * {@link #CONTENT_TYPE_MD_NAVI_HINT}<br>
     */
    @StringDef({
            ContentType.CONTENT_TYPE_MD_NAVI_HINT
    })
    @Retention(RetentionPolicy.SOURCE)
    @interface ContentType {

        /**
         * navigation hint<br>
         * 导航转向提示音(叮咚音)
         *
         * @since 11
         */
        String CONTENT_TYPE_MD_NAVI_HINT = "NAVI_HINT";

    }

    /**
     * get audio focus request System Content Type by Content Type Metadata {@link ContentType}<br>
     * 通过{@link ContentType}获取音频焦点请求 Content Type 类型
     *
     * @param contentType Content Type Metadata, such as {@link ContentType#CONTENT_TYPE_MD_NAVI_HINT}
     * @return System Content Type for audio focus request {@link android.media.AudioAttributes.Builder#setContentType(int)}
     * @see android.media.AudioManager#requestAudioFocus(AudioFocusRequest)
     * @see android.media.AudioFocusRequest.Builder#setAudioAttributes(AudioAttributes)
     * @see android.media.AudioAttributes.Builder#setUsage(int)
     * @see android.media.AudioAttributes.Builder#setContentType(int)
     * @see #getAudioAttributesUsage(String)
     * @since 11
     */
    int getAudioAttributesContentType(@ContentType String contentType);

}
