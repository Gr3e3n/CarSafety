/*
 * ECARX Technology Limited is the owner of the copyright and the trade secret of this software.
 * Without permission, no one has the right to obtain, disclose or use this software in any way.
 */
package ecarx.carext.base.exception;

/**
 * Thrown to indicate that the function is not implemented.<p>
 *
 * @since 1
 */
public class UnimplementedException extends UnsupportedOperationException {

    /**
     * Constructs an UnimplementedException with no detail message.
     *
     * @since 1
     */
    public UnimplementedException() {
        super("not implemented");
    }

    /**
     * Constructs an UnimplementedException with the specified
     * detail message.
     *
     * @param message the detail message
     * @since 1
     */
    public UnimplementedException(String message) {
        super(message);
    }
}
