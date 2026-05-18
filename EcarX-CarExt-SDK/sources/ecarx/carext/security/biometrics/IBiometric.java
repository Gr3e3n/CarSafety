/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.security.biometrics;

import ecarx.carext.base.exception.UnimplementedException;
import ecarx.carext.security.biometrics.face.IFaceManager;

/**
 * This class provides the API for accessing Biometric functionality.
 *
 * @since 6
 */
public interface IBiometric {

    /**
     * @return Returns a manager used to accessing face recognition functionality.
     * @since 6
     */
    default IFaceManager getFaceManager() {
        throw new UnimplementedException();
    }
}
