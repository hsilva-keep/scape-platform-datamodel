/*
   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package eu.scape_project.model;

import gov.loc.mix.v20.Mix;
import info.lc.xmlns.textmd_v3.TextMD;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.books.gbs.GbsType;

import edu.harvard.hul.ois.xml.ns.fits.fits_output.Fits;
import eu.scape_project.util.CopyUtil;

/**
*
* @author frank asseg
*
*/
@XmlRootElement(name = "file", namespace = "http://scape-project.eu/model")
public class File {

	@XmlAttribute(name = "mimetype")
	private final String mimetype;
	@XmlAttribute(name = "filename")
	private final String filename;
	@XmlAnyElement(lax = true)
    @XmlElementRefs({
        @XmlElementRef(name = "textMD", type = TextMD.class),
        @XmlElementRef(name = "fits", type = Fits.class),
        @XmlElementRef(name = "mix", type = Mix.class),
        @XmlElementRef(name = "gbs", type = GbsType.class),
        @XmlElementRef(name = "VIDEOMD", namespace = "http://www.loc.gov/videoMD/", type = JAXBElement.class),
        @XmlElementRef(name = "AUDIOMD", namespace = "http://www.loc.gov/audioMD/", type = JAXBElement.class)})
	private final Object technical;
	@XmlElement(name = "bitstream", namespace = "http://scape-project.eu/model")
	private final List<BitStream> bitStreams;
	@XmlElement(name = "uri", namespace = "http://scape-project.eu/model")
	private final URI uri;
	@XmlElement(name = "identifier", namespace = "http://scape-project.eu/model")
	private final Identifier identifier;

	private File() {
		this.filename = null;
		this.mimetype = null;
		this.technical = null;
		this.bitStreams = null;
		this.uri = null;
		this.identifier = null;
	}

	private File(Builder builder) {
		this.filename = builder.filename;
		this.mimetype = builder.mimetype;
		this.technical = builder.technical;
		this.bitStreams = (builder.bitStreams.isEmpty() ? null
				: new ArrayList<BitStream>(builder.bitStreams));
		this.uri = builder.uri;
		this.identifier = builder.identifier;
	}

	public List<BitStream> getBitStreams() {
		return bitStreams;
	}

	public Identifier getIdentifier() {
		return identifier;
	}

	public Object getTechnical() {
		return technical;
	}

	public URI getUri() {
		return uri;
	}

	public String getMimetype() {
		return mimetype;
	}

	public String getFilename() {
		return filename;
	}

	@Override
	public String toString() {
		return "File [technical=" + technical
				+ ", name=" + filename
				+ ", bitStreams=" + bitStreams
				+ ", uri=" + uri
				+ ", identifier=" + identifier
				+ ", mimetype=" + mimetype
				+ "]";
	}

	public static class Builder {
		private Object technical;
		private List<BitStream> bitStreams;
		private URI uri;
		private Identifier identifier;
		private String mimetype;
		private String filename;

		public Builder() {
			super();
			bitStreams = new ArrayList<BitStream>();
		}

		public Builder(File orig) {
			File copy = CopyUtil.deepCopy(File.class, orig);
			this.mimetype = copy.mimetype;
			this.technical = copy.technical;
			this.bitStreams = (copy.bitStreams == null ? new ArrayList<BitStream>()
					: copy.bitStreams);
			this.uri = copy.uri;
			this.identifier = copy.identifier;
			this.filename = copy.filename;
		}

		public Builder bitStreams(List<BitStream> bitStreams) {
			this.bitStreams = bitStreams;
			return this;
		}

		public Builder bitStream(BitStream bitStream) {
			bitStreams.add(bitStream);
			return this;
		}

		public File build() {
			return new File(this);
		}

		public Builder identifier(Identifier identifier) {
			this.identifier = identifier;
			return this;
		}

		public Builder technical(Object technical) {
			this.technical = technical;
			return this;
		}

		public Builder filename(String filename) {
			this.filename = filename;
			return this;
		}

		public Builder mimetype(String mimetype) {
			this.mimetype = mimetype;
			return this;
		}

		public Builder uri(URI uri) {
			this.uri = uri;
			return this;
		}
	}
}
