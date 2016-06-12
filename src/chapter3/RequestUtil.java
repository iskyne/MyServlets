package chapter3;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;

public class RequestUtil {
	
	public static void parseParameters(Map<String,String[]> map, String data,
            String encoding) {

        if ((data != null) && (data.length() > 0)) {

            // use the specified encoding to extract bytes out of the
            // given string so that the encoding is not lost.
            byte[] bytes = null;
            try {
                bytes = data.getBytes(Charset.forName(encoding));
                parseParameters(map, bytes, encoding);
            } catch (UnsupportedEncodingException uee) {
            	uee.printStackTrace();
            }

        }

    }

	/**
	 * Append request parameters from the specified String to the specified
	 * Map.  It is presumed that the specified Map is not accessed from any
	 * other thread, so no synchronization is performed.
	 * <p>
	 * <strong>IMPLEMENTATION NOTE</strong>:  URL decoding is performed
	 * individually on the parsed name and value elements, rather than on
	 * the entire query string ahead of time, to properly deal with the case
	 * where the name or value includes an encoded "=" or "&amp;" character
	 * that would otherwise be interpreted as a delimiter.
	 *
	 * NOTE: byte array data is modified by this method.  Caller beware.
	 *
	 * @param map Map that accumulates the resulting parameters
	 * @param data Input string containing request parameters
	 * @param encoding The encoding to use; if null, the default encoding is
	 * used
	 *
	 * @exception UnsupportedEncodingException if the requested encoding is not
	 * supported.
	 */
	public static void parseParameters(Map<String,String[]> map, byte[] data,
	        String encoding) throws UnsupportedEncodingException {
	
	    Charset charset = Charset.forName(encoding);
	
	    if (data != null && data.length > 0) {
	        int    ix = 0;
	        int    ox = 0;
	        String key = null;
	        String value = null;
	        while (ix < data.length) {
	            byte c = data[ix++];
	            switch ((char) c) {
	            case '&':
	                value = new String(data, 0, ox, charset);
	                if (key != null) {
	                    putMapEntry(map, key, value);
	                    key = null;
	                }
	                ox = 0;
	                break;
	            case '=':
	                if (key == null) {
	                    key = new String(data, 0, ox, charset);
	                    ox = 0;
	                } else {
	                    data[ox++] = c;
	                }
	                break;
	            case '+':
	                data[ox++] = (byte)' ';
	                break;
	            case '%':
	                data[ox++] = (byte)((convertHexDigit(data[ix++]) << 4)
	                                + convertHexDigit(data[ix++]));
	                break;
	            default:
	                data[ox++] = c;
	            }
	        }
	        //The last value does not end in '&'.  So save it now.
	        if (key != null) {
	            value = new String(data, 0, ox, charset);
	            putMapEntry(map, key, value);
	        }
	    }
	
	}
	/**
     * Put name and value pair in map.  When name already exist, add value
     * to array of values.
     *
     * @param map The map to populate
     * @param name The parameter name
     * @param value The parameter value
     */
    private static void putMapEntry( Map<String,String[]> map, String name,
            String value) {
        String[] newValues = null;
        String[] oldValues = map.get(name);
        if (oldValues == null) {
            newValues = new String[1];
            newValues[0] = value;
        } else {
            newValues = new String[oldValues.length + 1];
            System.arraycopy(oldValues, 0, newValues, 0, oldValues.length);
            newValues[oldValues.length] = value;
        }
        map.put(name, newValues);
    }
    
    /**
     * Convert a byte character value to hexadecimal digit value.
     *
     * @param b the character value byte
     */
    private static byte convertHexDigit( byte b ) {
        if ((b >= '0') && (b <= '9')) return (byte)(b - '0');
        if ((b >= 'a') && (b <= 'f')) return (byte)(b - 'a' + 10);
        if ((b >= 'A') && (b <= 'F')) return (byte)(b - 'A' + 10);
        throw new IllegalArgumentException("requestUtil.convertHexDigit.notHex");
    }
	public static List parseCookieReader(String header){
		ArrayList cookies=new ArrayList();
		if((header==null)||(header.length()<1)){
			return cookies;
		}
		
		while(header.length()>0){
			int semicolon=header.indexOf(";");
			if(semicolon<0){
				semicolon=header.length();
			}
			if(semicolon==0){
				break;
			}
			
			String token=header.substring(0,semicolon);
			if(semicolon<header.length()){
				header=header.substring(semicolon+1);
			}else{
				header="";
			}
			
			try{
				int equals=token.indexOf("=");
				if(equals>0){
					String name=token.substring(0,equals);
					String value=token.substring(equals+1);
					cookies.add(new Cookie(name,value));
				}
			}catch(Throwable e){
				e.printStackTrace();
			}
		}
		return cookies;
	}
}
