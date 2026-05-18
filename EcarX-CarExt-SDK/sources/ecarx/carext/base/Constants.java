package ecarx.carext.base;

public interface Constants {

    /**
     * File flag
     */
    int SS_RD = 0x01; // Read-only mode
    int SS_WR = 0x02; // Write-only mode
    int SS_RDWR = 0x04; // Read-write mode
    int SS_CREATE = 0x08; // Create if the specified file does not exist
    int SS_APPEND = 0x10; // Each write operation writes to the end of the file
    int SS_PERMANENT = 0x20; // Permanent storage
    int SS_NORMAL = 0x40; // Non-permanent storage
    int SS_INTERNAL = 0x80; // Stored in SPIROM, only for E05, support only on secure_storage with tee enabled, and SS_INTERNAL flag check enabled (not enabled by default)
    int SS_TRUNC = 0x100; // Clear the original file content, only for SS_NORMAL
    int SS_SHARE = 0x200; // Global shared file
    /**
     * Seek flag
     */
    int SS_SEEK_SET = 0; // Beginning of the file
    int SS_SEEK_CUR = 1; // Current position
    int SS_SEEK_END = 2; // End of the file
    /**
     * Error Code
     */
    int SS_SUCCESS = 0x00000000;
    int SS_ONLINE = 0x00000000;
    int SS_ERROR_BAD_PARAMETERS = 0xFFFFE201;
    int SS_ERROR_INVALID_INPUT_LENGTH = 0xFFFFE202;
    int SS_ERROR_OUT_OF_MEMORY = 0xFFFFE203;
    int SS_ERROR_ACCESS_CONFLICT = 0xFFFFE204;
    int SS_ERROR_STORAGE_NO_SPACE = 0xFFFFE205;
    int SS_ERROR_STORAGE_NOT_AVAILABLE = 0xFFFFE206;
    int SS_ERROR_OVERFLOW = 0xFFFFE207;
    int SS_ERROR_NOT_SUPPORTED = 0xFFFFE208;
    int SS_ERROR_CORRUPT_OBJECT = 0xFFFFE209;
    int SS_ERROR_ITEM_NOT_FOUND = 0xFFFFE210;
    int SS_OFFLINE = 0xFFFFE211;
    int SS_ERROR_SQL_INSERT = 0xFFFFE212;
    int SS_ERROR_SQL_NOT_FOUND = 0xFFFFE213;
    int SS_ERROR_SQL_PREPARE = 0xFFFFE214;
    int SS_ERROR_SQL_QUERY = 0xFFFFE215;
    int SS_ERROR_SQL_QUERY_ALL = 0xFFFFE216;
    int SS_ERROR_SQL_CTEATE_TABLE = 0xFFFFE217;
    int SS_ERROR_SQL_UNIQUE_INDEX = 0xFFFFE218;
    int SS_ERROR_SQL_DROP_TABLE = 0xFFFFE220;
    int SS_ERROR_SQL_DELETE = 0xFFFFE221;
    int SS_ERROR_SQL_BIND_PARAM = 0xFFFFE222;
    int SS_ERROR_SQL_UPDATE = 0xFFFFE223;
    int SS_ERROR_SQL_OPEN = 0xFFFFE224;
    int SS_ERROR_SESSION_ID_CREATE = 0xFFFFE225;
    int SS_ERROR_SESSION_ID_NOT_FOUND = 0xFFFFE226;
    int SS_ERROR_UNKNOWN_ERROR = 0xFFFFE2FF;
}
