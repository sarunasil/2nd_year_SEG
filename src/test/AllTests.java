/**
 * COMP2211 SEG Ad Auction Dashboard.
 */
package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * @author sarunasil
 *
 */

@RunWith(Suite.class)
@SuiteClasses({ ParserTesting.class, DbLoaderTesting.class, DataTesting.class })
public class AllTests {

}
