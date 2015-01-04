import java.io._
import java.util.Properties

import org.apache.commons.io.IOUtils

object PropertiesHelper {
  def writeProperties(f: File, ps: Map[String, String]): Unit = {
    val props = ps.foldLeft(new Properties){case (props, (k, v)) => props.setProperty(k, v); props}
    var out: OutputStream = null
    try {
      f.getParentFile.mkdirs()
      out = new FileOutputStream(f)
      props.store(out, null)
    } finally {
      IOUtils.closeQuietly(out)
    }
  }
}
