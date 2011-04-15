package Models;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/* Server returns for a Person :
 * <person>
 *     <birthday>1981-04-15T15:37:41.9056367+05:30</birthday>
 *     <address>
 *         <priority>1</priority>
 *         <addressText>1 Microsoft Way, Redmond</addressText>
 *     </address>
 *     <address>
 *         <priority>2</priority>
 *         <addressText>221B Baker Street, London</addressText>
 *     </address>
 * </person>
 */

@XStreamAlias("person")
public class Person {
    public Person() {
    }

    public Person(Date Birthday, ArrayList<Address> Addresses) {
        this.Birthday = Birthday;
        this.Addresses = Addresses;
    }

    @XStreamAlias("birthday")
    public Date Birthday;

    /*
    * .NET System.Xml.XmlSerializer cannot serialize Dictionaries and HashTables
    *
    * Quote MSDN:
    * "The XmlSerializer cannot process classes implementing the IDictionary interface.
    * This was partly due to schedule constraints and partly due to the fact that a hashtable
    * does not have a counterpart in the XSD type system. The only solution is to implement
    * a custom hashtable that does not implement the IDictionary interface."
    *
    * http://msdn.microsoft.com/en-us/library/ms950721.aspx - Look at the FAQ portion
    *
    * Throws a System.InvalidOperationException if you try it - Details are in 3rd level InnerException
    * Serializer throws Exceptions without messages xD
    *
    * xstream.net does this, but has some issues regarding verbosity and readability :
    * http://code.google.com/p/xstream-dot-net/issues/detail?id=18
    *
    * and the last release stopped @ Sep 23, 2009, used to happen every month. Activity in project is low.
    * http://code.google.com/p/xstream-dot-net/source/list
    *
    */

    // If Implicit is not given, it searches for Addresses element.
    @XStreamImplicit()
    public ArrayList<Address> Addresses;


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (Birthday != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            builder.append("Birthday : " + format.format(Birthday) + "\n");
        }
        if (Addresses != null) {
            builder.append("Addresses : \n");
            for (Address address : Addresses)
                builder.append("\t" + address.Priority + " : " + address.AddressText + "\n");
        }
        return builder.toString();
    }
}


