// Copyright © 2024 Andy Goryachev <andy@goryachev.com>
package goryachev.demo;
import goryachev.common.util.MurmurHash3;
import goryachev.demo.gallery.Gallery;
import goryachev.demo.gallery.GalleryFolder;
import goryachev.demo.gallery.GalleryItem;
import java.util.Random;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;


/**
 * Demo Data.
 */
public class DemoData
{
	private ObservableList<Message> messages;
	private Gallery gallery;
	
	
	public DemoData()
	{
	}
	
	
	public ObservableList<Message> getMessages()
	{
		if(messages == null)
		{
			messages = FXCollections.observableArrayList();
			
			String text =
				"""
				From Wikipedia, the free encyclopedia
				
				In computer programming, an application framework[1] consists of a software framework used by software developers to implement the standard structure of application software.[2]
				
				Application frameworks became popular with the rise of graphical user interfaces (GUIs), since these tended to promote a standard structure for applications. Programmers find it much simpler to create automatic GUI creation tools when using a standard framework, since this defines the underlying code structure of the application in advance. Developers usually use object-oriented programming (OOP) techniques to implement frameworks such that the unique parts of an application can simply inherit from classes extant in the framework.[citation needed]
				
				Examples
				========
				
				Apple Computer developed one of the first commercial application frameworks, MacApp (first release 1985), for the Macintosh. Originally written in an extended (object-oriented) version of Pascal termed Object Pascal, it was later rewritten in C++. Another notable framework for the Mac is Metrowerks' PowerPlant, based on Carbon. Cocoa for macOS offers a different approach to an application framework, based on the OpenStep framework developed at NeXT.
				
				Since the 2010s, many apps have been created with the frameworks based on Google's Chromium project. The two prominent ones are Electron and the Chromium Embedded Framework.
				
				Free and open-source software frameworks exist as part of the Mozilla, LibreOffice, GNOME, KDE, NetBeans, and Eclipse projects.
				
				Microsoft markets a framework for developing Windows applications in C++ called the Microsoft Foundation Class Library, and a similar framework for developing applications with Visual Basic or C#, named .NET Framework.
				
				Several frameworks can build cross-platform applications for Linux, Macintosh, and Windows from common source code, such as Qt, wxWidgets, Juce, Fox toolkit, or Eclipse Rich Client Platform (RCP).
				
				Oracle Application Development Framework (Oracle ADF) aids in producing Java-oriented systems.
				
				Silicon Laboratories offers an embedded application framework for developing wireless applications on its series of wireless chips.
				
				MARTHA is a proprietary software Java framework that all of the RealObjects software is built on. 
				""";
			
			String license =
				"""
			                                 Apache License
			                           Version 2.0, January 2004
			                        http://www.apache.org/licenses/
			
			   TERMS AND CONDITIONS FOR USE, REPRODUCTION, AND DISTRIBUTION
			
			   1. Definitions.
			
			      "License" shall mean the terms and conditions for use, reproduction,
			      and distribution as defined by Sections 1 through 9 of this document.
			
			      "Licensor" shall mean the copyright owner or entity authorized by
			      the copyright owner that is granting the License.
			
			      "Legal Entity" shall mean the union of the acting entity and all
			      other entities that control, are controlled by, or are under common
			      control with that entity. For the purposes of this definition,
			      "control" means (i) the power, direct or indirect, to cause the
			      direction or management of such entity, whether by contract or
			      otherwise, or (ii) ownership of fifty percent (50%) or more of the
			      outstanding shares, or (iii) beneficial ownership of such entity.
			
			      "You" (or "Your") shall mean an individual or Legal Entity
			      exercising permissions granted by this License.
			
			      "Source" form shall mean the preferred form for making modifications,
			      including but not limited to software source code, documentation
			      source, and configuration files.
			
			      "Object" form shall mean any form resulting from mechanical
			      transformation or translation of a Source form, including but
			      not limited to compiled object code, generated documentation,
			      and conversions to other media types.
			
			      "Work" shall mean the work of authorship, whether in Source or
			      Object form, made available under the License, as indicated by a
			      copyright notice that is included in or attached to the work
			      (an example is provided in the Appendix below).
			
			      "Derivative Works" shall mean any work, whether in Source or Object
			      form, that is based on (or derived from) the Work and for which the
			      editorial revisions, annotations, elaborations, or other modifications
			      represent, as a whole, an original work of authorship. For the purposes
			      of this License, Derivative Works shall not include works that remain
			      separable from, or merely link (or bind by name) to the interfaces of,
			      the Work and Derivative Works thereof.
			
			      "Contribution" shall mean any work of authorship, including
			      the original version of the Work and any modifications or additions
			      to that Work or Derivative Works thereof, that is intentionally
			      submitted to Licensor for inclusion in the Work by the copyright owner
			      or by an individual or Legal Entity authorized to submit on behalf of
			      the copyright owner. For the purposes of this definition, "submitted"
			      means any form of electronic, verbal, or written communication sent
			      to the Licensor or its representatives, including but not limited to
			      communication on electronic mailing lists, source code control systems,
			      and issue tracking systems that are managed by, or on behalf of, the
			      Licensor for the purpose of discussing and improving the Work, but
			      excluding communication that is conspicuously marked or otherwise
			      designated in writing by the copyright owner as "Not a Contribution."
			
			      "Contributor" shall mean Licensor and any individual or Legal Entity
			      on behalf of whom a Contribution has been received by Licensor and
			      subsequently incorporated within the Work.
			
			   2. Grant of Copyright License. Subject to the terms and conditions of
			      this License, each Contributor hereby grants to You a perpetual,
			      worldwide, non-exclusive, no-charge, royalty-free, irrevocable
			      copyright license to reproduce, prepare Derivative Works of,
			      publicly display, publicly perform, sublicense, and distribute the
			      Work and such Derivative Works in Source or Object form.
			
			   3. Grant of Patent License. Subject to the terms and conditions of
			      this License, each Contributor hereby grants to You a perpetual,
			      worldwide, non-exclusive, no-charge, royalty-free, irrevocable
			      (except as stated in this section) patent license to make, have made,
			      use, offer to sell, sell, import, and otherwise transfer the Work,
			      where such license applies only to those patent claims licensable
			      by such Contributor that are necessarily infringed by their
			      Contribution(s) alone or by combination of their Contribution(s)
			      with the Work to which such Contribution(s) was submitted. If You
			      institute patent litigation against any entity (including a
			      cross-claim or counterclaim in a lawsuit) alleging that the Work
			      or a Contribution incorporated within the Work constitutes direct
			      or contributory patent infringement, then any patent licenses
			      granted to You under this License for that Work shall terminate
			      as of the date such litigation is filed.
			
			   4. Redistribution. You may reproduce and distribute copies of the
			      Work or Derivative Works thereof in any medium, with or without
			      modifications, and in Source or Object form, provided that You
			      meet the following conditions:
			
			      (a) You must give any other recipients of the Work or
			          Derivative Works a copy of this License; and
			
			      (b) You must cause any modified files to carry prominent notices
			          stating that You changed the files; and
			
			      (c) You must retain, in the Source form of any Derivative Works
			          that You distribute, all copyright, patent, trademark, and
			          attribution notices from the Source form of the Work,
			          excluding those notices that do not pertain to any part of
			          the Derivative Works; and
			
			      (d) If the Work includes a "NOTICE" text file as part of its
			          distribution, then any Derivative Works that You distribute must
			          include a readable copy of the attribution notices contained
			          within such NOTICE file, excluding those notices that do not
			          pertain to any part of the Derivative Works, in at least one
			          of the following places: within a NOTICE text file distributed
			          as part of the Derivative Works; within the Source form or
			          documentation, if provided along with the Derivative Works; or,
			          within a display generated by the Derivative Works, if and
			          wherever such third-party notices normally appear. The contents
			          of the NOTICE file are for informational purposes only and
			          do not modify the License. You may add Your own attribution
			          notices within Derivative Works that You distribute, alongside
			          or as an addendum to the NOTICE text from the Work, provided
			          that such additional attribution notices cannot be construed
			          as modifying the License.
			
			      You may add Your own copyright statement to Your modifications and
			      may provide additional or different license terms and conditions
			      for use, reproduction, or distribution of Your modifications, or
			      for any such Derivative Works as a whole, provided Your use,
			      reproduction, and distribution of the Work otherwise complies with
			      the conditions stated in this License.
			
			   5. Submission of Contributions. Unless You explicitly state otherwise,
			      any Contribution intentionally submitted for inclusion in the Work
			      by You to the Licensor shall be under the terms and conditions of
			      this License, without any additional terms or conditions.
			      Notwithstanding the above, nothing herein shall supersede or modify
			      the terms of any separate license agreement you may have executed
			      with Licensor regarding such Contributions.
			
			   6. Trademarks. This License does not grant permission to use the trade
			      names, trademarks, service marks, or product names of the Licensor,
			      except as required for reasonable and customary use in describing the
			      origin of the Work and reproducing the content of the NOTICE file.
			
			   7. Disclaimer of Warranty. Unless required by applicable law or
			      agreed to in writing, Licensor provides the Work (and each
			      Contributor provides its Contributions) on an "AS IS" BASIS,
			      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
			      implied, including, without limitation, any warranties or conditions
			      of TITLE, NON-INFRINGEMENT, MERCHANTABILITY, or FITNESS FOR A
			      PARTICULAR PURPOSE. You are solely responsible for determining the
			      appropriateness of using or redistributing the Work and assume any
			      risks associated with Your exercise of permissions under this License.
			
			   8. Limitation of Liability. In no event and under no legal theory,
			      whether in tort (including negligence), contract, or otherwise,
			      unless required by applicable law (such as deliberate and grossly
			      negligent acts) or agreed to in writing, shall any Contributor be
			      liable to You for damages, including any direct, indirect, special,
			      incidental, or consequential damages of any character arising as a
			      result of this License or out of the use or inability to use the
			      Work (including but not limited to damages for loss of goodwill,
			      work stoppage, computer failure or malfunction, or any and all
			      other commercial damages or losses), even if such Contributor
			      has been advised of the possibility of such damages.
			
			   9. Accepting Warranty or Additional Liability. While redistributing
			      the Work or Derivative Works thereof, You may choose to offer,
			      and charge a fee for, acceptance of support, warranty, indemnity,
			      or other liability obligations and/or rights consistent with this
			      License. However, in accepting such obligations, You may act only
			      on Your own behalf and on Your sole responsibility, not on behalf
			      of any other Contributor, and only if You agree to indemnify,
			      defend, and hold each Contributor harmless for any liability
			      incurred by, or claims asserted against, such Contributor by reason
			      of your accepting any such warranty or additional liability.
			
			   END OF TERMS AND CONDITIONS
			
			   APPENDIX: How to apply the Apache License to your work.
			
			      To apply the Apache License to your work, attach the following
			      boilerplate notice, with the fields enclosed by brackets "[]"
			      replaced with your own identifying information. (Don't include
			      the brackets!)  The text should be enclosed in the appropriate
			      comment syntax for the file format. We also recommend that a
			      file or class name and description of purpose be included on the
			      same "printed page" as the copyright notice for easier
			      identification within third-party archives.
			
			   Copyright [yyyy] [name of copyright owner]
			
			   Licensed under the Apache License, Version 2.0 (the "License");
			   you may not use this file except in compliance with the License.
			   You may obtain a copy of the License at
			
			       http://www.apache.org/licenses/LICENSE-2.0
			
			   Unless required by applicable law or agreed to in writing, software
			   distributed under the License is distributed on an "AS IS" BASIS,
			   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
			   See the License for the specific language governing permissions and
			   limitations under the License.
			""";
			
			messages.setAll
			(
				new Message("Application Framework", text),
				new Message("License", license),
				new Message("Notes", "1.\n2.\n3.")
			);
		}
		return messages;
	}

	
	public Gallery getGallery()
	{
		if(gallery == null)
		{
			gallery = createGallery();
		}
		return gallery;
	}


	private static Gallery createGallery()
	{
		Gallery g = new Gallery();
		for(int i=0; i<20; i++)
		{
			g.getFolders().add(mkFolder(i));
		}
		return g;
	}


	private static GalleryFolder mkFolder(int seq)
	{
		GalleryFolder f = new GalleryFolder("Folder " + seq);
		Random r = new Random();
		int num = 1 + r.nextInt(3);
		int max;
		switch(num)
		{
		case 1:
			max = 10;
			break;
		case 2:
			max = 100;
			break;
		case 3:
		default:
			max = 1000;
			break;
		}
		int sz = r.nextInt(max);
		
		for(int i=0; i<sz; i++)
		{
			f.getItems().add(mkItem(seq, i));
		}
		return f;
	}


	private static GalleryItem mkItem(int seq, int num)
	{
		int x = MurmurHash3.hash(seq + "." + num, 0) & 0x7fffffff;
		double hue = (x % 360);
		Color col = Color.hsb(hue, 0.5, 0.5);
		
		return new GalleryItem()
		{
			public byte[] getOriginal()
			{
				return null;
			}
			
			
			public Image getImage(double width, double height)
			{
				Canvas c = new Canvas(width, height);
				GraphicsContext g = c.getGraphicsContext2D();
				g.setFill(col);
				g.fillRect(0, 0, width, height);
				return c.snapshot(null, null);
			}
		};
	}
}
