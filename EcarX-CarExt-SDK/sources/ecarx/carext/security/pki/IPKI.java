/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.security.pki;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;

import ecarx.carext.base.BaseAPI;
import ecarx.carext.base.Permissions;
import ecarx.carext.base.exception.UnimplementedException;

/**
 * This class provides the API for accessing PKI functionality.
 *
 * @since 6
 */
public interface IPKI extends BaseAPI {

    /**
     * Request permission for KeyChain
     *
     * @return Authorization success status
     * @since 6
     */
    @RequiresPermission(Permissions.ACCESS_PKI)
    default boolean grantPermissionForKeyChain() {
        throw new UnimplementedException();
    }

    /**
     * Check self permission for KeyChain
     *
     * @return Authorization status true or false
     * @since 6
     */
    @RequiresPermission(Permissions.ACCESS_PKI)
    default boolean isKeyChainGranted() {
        throw new UnimplementedException();
    }

    /**
     * Return the PKI current status
     *
     * @return ture is prepared
     * @since 6
     */
    @RequiresPermission(Permissions.ACCESS_PKI)
    default boolean isPrepared() {
        throw new UnimplementedException();
    }

    /**
     * Listen to PKI prepare status
     *
     * @param listener the listener
     * @return true register success
     * @since 6
     */
    @RequiresPermission(Permissions.ACCESS_PKI)
    default boolean registerPKIPreparedListener(@NonNull PKIPreparedListener listener) {
        throw new UnimplementedException();
    }

    /**
     * Remove listen to PKI prepare status
     *
     * @param listener the listener
     * @return true unregister success
     * @since 6
     */
    @RequiresPermission(Permissions.ACCESS_PKI)
    default boolean unregisterPKIPreparedListener(@NonNull PKIPreparedListener listener) {
        throw new UnimplementedException();
    }

    /**
     * Get certificate alias
     *
     * @return Certificate alias
     * @since 6
     */
    @NonNull
    @RequiresPermission(Permissions.ACCESS_PKI)
    default String getCertificateAlias() {
        throw new UnimplementedException();
    }

    /**
     * Get Root CA Certificate
     * Return null before certificate installation success
     *
     * @return cert data
     * @since 6
     */
    @Nullable
    @RequiresPermission(Permissions.ACCESS_PKI_CA)
    default byte[] getRootCA() {
        throw new UnimplementedException();
    }

    /**
     * Get Global Policy CA Certificate
     * Return null before certificate installation success
     *
     * @return cert data
     * @since 6
     */
    @Nullable
    @RequiresPermission(Permissions.ACCESS_PKI_CA)
    default byte[] getGlobalPolicyCA() {
        throw new UnimplementedException();
    }

    /**
     * Get ECUIssuing Certificate
     * Return null before certificate installation success
     *
     * @return cert data
     * @since 6
     */
    @Nullable
    @RequiresPermission(Permissions.ACCESS_PKI_CA)
    default byte[] getECUIssuingCA() {
        throw new UnimplementedException();
    }

    /**
     * Get ServicesIssuing Certificate
     * Return null before certificate installation success
     *
     * @return cert data
     * @since 6
     */
    @Nullable
    @RequiresPermission(Permissions.ACCESS_PKI_CA)
    default byte[] getServicesIssuingCA() {
        throw new UnimplementedException();
    }
}
