/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.audio;

import ecarx.carext.base.BaseAPI;
import ecarx.carext.base.exception.UnimplementedException;

/**
 * This class provides the API for accessing audio functionality
 *
 * @since 11
 */
public interface IAudio extends BaseAPI {
    /**
     * @return get customize AudioAttributes instance
     * @see IAudioAttributes
     * 
     * @since 11
     */
    default IAudioAttributes getAudioAttributes() {
        throw new UnimplementedException();
    }
}
