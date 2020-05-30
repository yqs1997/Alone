package MyClass;
import java.util.Arrays;
import java.util.Scanner;

public class YuceTable {
	private String left[];  //�󲿼��ϣ������ظ����󲿣�
	private String right[];  //�Ҳ�����
	private Model members[];  //�ķ���Ա����
	private String T[];   //�ս����
	private String NT[];  //���ս����
	private String table[][];  //���ɵ�Ԥ�������
	private String noLeftG[];  //û����ݹ���ķ�G
	private String first[];
	private String follow[];
	
	public YuceTable(String G[]) {
		String G_right[] = new String[10];
		String G_left[] =  new String[ G.length ];
		for ( int i = 0; i < G.length; i++ ) {
			G_left[i] = G[i].charAt(0) + "";
			G_right[i] = G[i].substring(4);
		}
		
		G = removeLeftRecursion( G_left, G_right ); //����ݹ�
		this.noLeftG = G;
		
		System.out.println( "��ȥ��ݹ�����£�" );
		for ( int i = 0; i < G.length; i++ ) {
			System.out.println( G[i] );
		}
		
		getLeftRight(G); //�õ����Ҳ���
		for ( int i = 0; i < this.left.length; i++ ) {
			this.members[i].setFirst( setFirst( members[i].getRight() ) );  //��ÿ�������first��
		}
		for ( int i = 0; i < this.members.length; i++ ) { //��ÿ��������Ƿ��Ƴ���
			for ( int j = 0; j < this.members[i].getFirst().length; j++) {
				if ( this.members[i].getFirst()[j].equals("��") ) {
					this.members[i].setIsNull( "Y" );
					break;
				}
				this.members[i].setIsNull( "N" );
			}
		}
		for ( int i = 0; i < this.left.length; i++ ) {  //��ÿ�������follow��
			this.members[i].setFollow( setFollow( members[i].getLeft() ) );
		}
		
		/* ����Ϊ�ս���źͷ��ս���ŵ�ȥ�ش��� */
		for ( int i = 0; i < this.members.length; i++) {
			String str1[] = this.members[i].getFirst();
			String str2[] = this.members[i].getFollow();
//			System.out.print("T��");   //�������
//			for ( int j = 0; j < str1.length ; j++) {
//				System.out.print( str1[j] +" ");
//			}
//			System.out.println();
//			System.out.print("NT��");
//			for ( int j = 0; j < str2.length ; j++) {
//				System.out.print( str2[j] +" ");
//			}
//			System.out.println();
		}
		String T = "";
		String NT = "";
		for ( int i = 0; i < this.members.length; i++ ) {
			if ( NT.indexOf( this.members[i].getLeft() ) == -1) { //�����ھͷ�����ս����
				NT += this.members[i].getLeft();
			}
		}
		for ( int i = 0; i < this.members.length; i++ ) {
			for ( int j = 0; j < this.members[i].getRight().length(); j++) {
				if ( ( T.indexOf( this.members[i].getRight().charAt(j) ) ) == -1 && !( this.members[i].getRight().charAt(j) + "" ).matches("[A-Z]") && !( this.members[i].getRight().charAt(j) + "" ).equals("��")) {
					T += this.members[i].getRight().charAt(j);
				}
			}
		}
		T += "#"; //�ս������#��
		
		/*������ַ���תΪ����*/
		this.T = new String[ T.length() ];
		for ( int i = 0; i < T.length(); i++ ) {
			this.T[i] = T.charAt(i) + "" ;
		}
		this.NT = new String[ NT.length() ];
		for ( int i = 0; i < NT.length(); i++ ) {
			this.NT[i] = NT.charAt(i) + "" ;
		}
		printTable();
		this.first = new String [this.NT.length];
		this.follow = new String [ this.NT.length];
		for ( int i = 0; i < this.NT.length; i++ ) {
			String first = "";
			for ( int j = 0; j < this.members.length; j++ ) {
				if ( this.members[j].getLeft().equals( this.NT[i] ) ) {
					for ( int k = 0; k < this.members[j].getFirst().length; k++) {
						if ( first.indexOf( this.members[j].getFirst()[k] ) == -1 ) {
							first += this.members[j].getFirst()[k];
						}
					}
				}
			}
			this.first[i] = first;
		}
		for ( int i = 0; i < this.NT.length; i++ ) {
			String follow = "";
			for ( int j = 0; j < this.members.length; j++ ) {
				if ( this.members[j].getLeft().equals( this.NT[i] ) ) {
					for ( int k = 0; k < this.members[j].getFollow().length; k++) {
						if ( follow.indexOf( this.members[j].getFollow()[k] ) == -1 ) {
							follow += this.members[j].getFollow()[k];
						}
					}
				}
			}
			this.follow[i] = follow;
		}
//		for( int i = 0; i<this.first.length;i++){
//			System.out.println(this.first[i]  +"***"+ this.follow[i]);
//		}
	}
	
	public void printTable() {
		/*����Ϊ����Ԥ����ŵ���ά����table��*/
		this.table = new String[ this.NT.length ][ this.T.length ];
		for ( int i = 0; i < this.NT.length; i++) {  //��ÿһ�����ս���Ų�ѭ��
			for ( int s = 0; s < this.T.length; s++ ) {  //��ÿһ���ս������ѭ��
				int r = 0; //�ҵ���ʶ��
				for ( int j = 0; j < this.members.length; j++ ) {   //��ÿһ���ķ�������ѭ��
					if ( this.members[j].getLeft().equals( this.NT[i] ) ) { //����ö�����󲿺ʹ˲�ķ��ս�����
						if ( this.members[j].getRight().equals( "��" ) ) {  //����Ҳ�Ϊ�գ�����follow���в���
							for ( int k = 0; k < this.members[j].getFollow().length; k++ ) { //�ڶ����follow����ѭ��
								if ( this.members[j].getFollow()[k].equals( this.T[s] ) ) {  //�����ʱ���ս���ͱ��е��ս�����
									table[i][s] = this.members[j].getRight();  //���Ҳ����뼯��table��
									r = 1;  //�ҵ�
									break;
								}
							}
						}else {  //����Ҳ��ǿգ���ֱ����first�����в���
							for ( int k = 0; k < this.members[j].getFirst().length; k++ ) { //�ڶ����first����ѭ��
								if ( this.members[j].getFirst()[k].equals( this.T[s] ) ) { //�����ʱ���ս���ͱ��е��ս�����
									table[i][s] = this.members[j].getRight(); //���Ҳ����뼯��table��
									r = 1; //�ҵ�
									break;
								}
							}
						}
					}
				}
				if ( r == 0 ) {  //һ���е�ѭ������  ��r��Ϊ��  ��û���κ�ƥ���table�д桰 ��
					table[i][s] = " ";
				}else {  //����  ������rΪ0��Ϊ��һ��ѭ����׼��
					r = 0;
				}
				
			}
		}
		/*���²�����ӡԤ�������*/
		System.out.println();
		System.out.println("Ԥ�������");
		System.out.print("\t");
		for ( int i = 0; i < this.T.length; i++) {
			System.out.print( this.T[i] + "\t" );
		}
		System.out.println();
		for ( int i = 0; i < table.length ; i++) {
			System.out.print( this.NT[i] + "\t" );
			for ( int j = 0; j < table[i].length; j++ ) {
				if ( !table[i][j].equals(" "))
				{
					System.out.print( this.NT[i] + "::=" + table[i][j] + "\t" );
				}else {
					System.out.print( table[i][j] + "\t" );
				}
			}
			System.out.println();
		}
	}
	
	public String[] removeLeftRecursion( String G_left[], String G_right[] ) { //��ȥ��ݹ�
		int len = G_left.length;
		String GG[][] = new String[len][5]; //���ķ�Ϊ��ά����
		String GG_isLR[][] = new String[len][5];//�Ƿ������ݹ�
		String G_isLR[] = new String[len];
		for ( int i = 0; i < len; i++ ) {
			int GGindex = 0;
			int begin_index = 0;
			int end_index = G_right[i].indexOf( "|" );
			int r = 0;
			while ( end_index != -1 ) {
				GG[i][GGindex ] = G_right[i].substring(begin_index, end_index);
				if ( ( G_right[i].charAt(begin_index) + "" ).equals( G_left[i] ) ) {
					GG_isLR[i][ GGindex ] = "Y";
					r = 1;
				}else{
					GG_isLR[i][ GGindex ] = "N";
				}
				GGindex++;
				begin_index = end_index + 1;
				end_index = G_right[i].indexOf( "|", begin_index );
			}
			GG[i][ GGindex ] = G_right[i].substring( begin_index );
			if ( ( G_right[i].charAt(begin_index) + "" ).equals( G_left[i] ) ) {
				GG_isLR[i][ GGindex ] = "Y";
				r = 1;
			}else{
				GG_isLR[i][ GGindex ] = "N";
			}
			if ( r == 1 ) {
				G_isLR[i] = "Y";
			}else {
				G_isLR[i] = "N";
			}
		}
		String new_G[] = new String[10];
		int G_index = 0;
		for ( int i = 0; i < G_left.length; i++ ) {
			String temp = "";
			String temp2 = "";
			if ( G_isLR[i].equals( "Y" ) ) {//������ݹ�
				char new_left = (char) (G_left[i].charAt(0) + 1); //�µ���
				int r = 0;
				for( int k = 0; k < G_left.length; k++) {
					if ( G_left[k].equals( new_left + "" ) ) {
						r = 1;
						break;
					}
				}
				while ( r == 1) {
					r = 0;
					new_left = (char) ( new_left + 1); //���������µ���
					for( int k = 0; k < G_left.length; k++) {
						if ( G_left[k].equals( new_left ) ) {
							r = 1;
							break;
						}
					}
				}
				temp += new_left + "::=";
				temp2 += G_left[i] + "::=";
				for ( int j = 0; GG[i][j] != null; j++ ) {
					if ( GG_isLR[i][j].equals( "Y" ) ) {
						temp += GG[i][j].substring(1) + new_left + "|";
					}else {
						temp2 += GG[i][j] + new_left + "|";
					}
				}
				temp += "��"; 
				temp2 = temp2.substring( 0, temp2.length()-1 ); //ȥ������|
				new_G[ G_index++ ] = temp2;
				new_G[ G_index++ ] = temp;
			}else { //��������ݹ�
				String t = G_left[i] + "::=" + G_right[i];
				new_G[ G_index++ ] = t;
			}
		}
		String now_G[] = new String[ G_index ];
		for ( int i = 0; i < now_G.length; i++ ) {
			now_G[i] = new_G[i];
	//		System.out.println( now_G[i] );
		}
		System.out.println();
		return now_G;
	}
	
	public void getLeftRight( String G[] ) {

		int len = G.length;
		int sum = 0;
		
		//�������ķ�������
		for( int i = 0; i<len; i++ ) {
			int temp = getTotalOfG( G[i] );
			if( temp == 0 ) {
				sum++;
			}else {
				sum += temp+1;
			}
		}
		
		//����ռ�
		this.left = new String[sum];
		this.right = new String[sum];
		
		int k=0;
		
		//�����ķ�G����û��|���ʾֻ��һ���Ҳ���ֱ�ӷ�������Ҳ�
		//������|���ʾ�ж���Ҳ�������Ҫ�Ƚ��Ҳ�����ɶ���ķ�����
		//�����ν������Ҳ��ķ���
		for( int i = 0; i < len; i++) {
			int index = G[i].indexOf("|");
			if( index == -1 ) {
				this.left[k] = getLeftPart( G[i] );
				this.right[k] = getRightPart( G[i] );
				k++;
			}else {
				String str[] = getPeerGG( getRightPart( G[i] ) );
				int size = str.length;
				String temp = G[i];
				for( int j = 0; j < size; j++ ) {
					this.left[k] = getLeftPart( temp );
					this.right[k] = str[j];
					k++;
				}
			}
		}
		this.members = new Model[ this.left.length ];
		for ( int i = 0; i < this.members.length; i++) {
			this.members[i] = new Model();
		}
		for ( int i = 0; i < this.left.length; i++ ) {
			this.members[i].setLeft( this.left[i] );
			this.members[i].setRight( this.right[i] );
		}
	}
	
	//���븴���ķ�
	public String[] getPeerGG(String GG) {
		int preLen,nowLen;//GG�ַ���ǰ��任�ĳ���
		String string[];
		int number = getTotalOfG(GG);
		string = new String[number+1]; //�ķ��ĸ���
		int index1 = 0;
		for(int i = 0; i<number; i++) {
			int index2 = GG.indexOf("|", index1); //������һ��|���±�
			string[i] = GG.substring(index1, index2);
			index1 = index2+1;
		}
		string[number] = GG.substring(index1);
		return string;
	}
	
	//�����ķ����ܸ���
	public int getTotalOfG(String GG) {
		int number = 0;
		for( int i = 0; i < GG.length(); i++) {
			if(GG.charAt(i)=='|') {
				number++;
			}
		}
		return number;
	}
	
	//�������
	public String getLeftPart(String GG) {
		return GG.charAt(0)+"";
	}
	
	//������Ҳ�
	public String getRightPart(String GG) {
		if( ( GG.charAt(1) + "" ).equals("-") ) { //��������ķ�ΪE->xxxx��ʽ
			return GG.substring(3);
		}else {  //����ӦΪE::=xxxx��ʽ
			return GG.substring(4);
		}
	}
	
	public String[] setFirst( String GG ) {
		String s = "";
		String str[];
		int r = 0 ;
		int index;
		for ( index = 0; index < GG.length(); index++ ){
			if ( !( GG.charAt(index) + "" ).matches("[A-Z]") ){
				r = 1;
				break;
			}
		}
//		if ( !( GG.charAt(0) + "" ).matches("[A-Z]") || ( GG.charAt(0) + "" ).equals( "��" ) ) { //���Ϊ�ս��
//			if ( s.indexOf( GG.charAt(0) + "" ) == -1) { //ȥ�أ������ڵķ��ս���żӵ��ַ�����
//				s += GG.charAt(0) + "";
//			}
//		}
		if ( r == 1 ){  //�����ս��
			if ( s.indexOf( GG.charAt(index) + "" ) == -1) { //ȥ�أ������ڵķ��ս���żӵ��ַ�����
				s += GG.charAt(index) + "";
			}
		}else { //û���ս��
			String temp[] = getNTFirst( GG.charAt(0) + "" ); //�ݹ�Ѱ���ս��
			for( int j = 0; j < temp.length; j++) {
				if ( temp[j] != null) {
					if ( s.indexOf( temp[j] ) == -1) {
						s += temp[j];
					}
				}
			}
		}
		int sLen = s.length();
		str = new String[ sLen ];
		for( int i = 0; i < sLen; i++) { //���ַ���ת��Ϊ����
			str[i] = s.charAt(i) + "";
		}
		return str;
	}
	
	public String[] getNTFirst( String GG ) {
		int leftLen = this.left.length;
		String s = "";
		String str[];
		for ( int i = 0; i < leftLen; i++) {
			if ( this.left[i].equals(GG) ) {
//				if ( !( right[i].charAt(0) + "" ).matches("[A-Z]") || ( right[i].charAt(0) + "" ).equals( "��" ) ) { //���ΪСд ��Ϊ�ս��
//					if ( s.indexOf( right[i].charAt(0) + "" ) == -1) { //ȥ�أ������ڵķ��ս���żӵ��ַ�����
//						s += right[i].charAt(0) + "";
//					}
				int index;
				int r = 0;
				for ( index = 0; index < right[i].length(); index++ ){
					if ( !( right[i].charAt(index) + "" ).matches("[A-Z]") ){
						r = 1;
						break;
					}
				}
				if ( r == 1 ){  //�����ս��
					if ( s.indexOf( right[i].charAt(index) + "" ) == -1) { //ȥ�أ������ڵķ��ս���żӵ��ַ�����
						s += right[i].charAt(index) + "";
					}
				}
				else { //�������ս��
					String temp[] = getNTFirst( right[i].charAt(0) + "" ); //�ݹ�Ѱ���ս��
					for( int j = 0; j < temp.length; j++) {
						if ( temp[j] != null) {
							if ( s.indexOf( temp[j] ) == -1) {
								s += temp[j];
							}
						}
					}
				}
			}
		}
		int sLen = s.length();
		str = new String[ sLen ];
		for( int i = 0; i < sLen; i++) { //���ַ���ת��Ϊ����
			str[i] = s.charAt(i) + "";
		}
		return str;
	}
	
	public String[] setFollow( String GG ) {
		int rightLen = this.right.length;
		String s = "";
		String str[];
		if ( GG.equals( this.left[0] ) ) {  //���Ϊ��ʼ����
			if ( s.indexOf( "#" ) == -1) {
				s += "#";
			}
		}
		for ( int i = 0; i < rightLen; i++ ) {
			int index = this.right[i].indexOf(GG);
			if ( index != -1) {
				if ( index == this.right[i].length()-1 ) {//�Ƿ�Ϊ���һλ
					if ( s.indexOf( "#" ) == -1 ) {
						s += "#";
					}
					//���󲿵�Follow
					if ( !this.left[i].equals( GG )) {
						String temp[] = setFollow( this.left[i] );
						for ( int q = 0; q < temp.length; q++) {
							if ( s.indexOf( temp[q] ) == -1 ) {
								s += temp[q];
							}
						}
					}
				}else { //�������һλ
					if ( !( right[i].charAt( index+1 ) + "" ).matches("[A-Z]") ) {//����ս����
						if ( s.indexOf( right[i].charAt( index+1 ) + "" ) == -1 ) {
							s += right[i].charAt( index+1 ) + "";
						}
					}else { //������ս��
						for (int j = 0; j < this.members.length; j++) {
							if ( this.members[j].getLeft().equals( right[i].charAt( index+1 ) + "" ) ) {
								for ( int k = 0; k < this.members[j].getFirst().length; k++) { //�Ѹ��ս���ŵ�first����ֵ��s
									if ( ( s.indexOf( this.members[j].getFirst()[k] ) == -1 ) && !this.members[j].getFirst()[k].equals( "��" ) ) {
										s += this.members[j].getFirst()[k];
									}
								}
								if ( this.members[j].getIsNull().equals( "Y" ) ) { //���÷����а����գ����ټ��ϴ�ʱ�󲿵�follow��
									String leftFollow[];
									if ( this.members[i].getFollow() == null ){
										leftFollow = setFollow( this.members[i].getLeft() );
									}else{
										leftFollow = this.members[i].getFollow();
									}
									for ( int u = 0; u < leftFollow.length; u++) {
										if ( leftFollow[u] != null && s.indexOf(leftFollow[u] ) == -1 )
											s += leftFollow[u];
									}
								}
							}
						}
					}
				}
			}
		}
		int sLen = s.length();
		str = new String[ sLen ];
		for( int i = 0; i < sLen; i++) { //���ַ���ת��Ϊ����
			str[i] = s.charAt(i) + "";
		}
		return str;
	}
	
	@Override
	public String toString() {
		return "FirstFollowSelect [left=" + Arrays.toString(left) + ", right=" + Arrays.toString(right) + "]";
	}

	public String[] getLeft() {
		return left;
	}

	public void setLeft(String[] left) {
		this.left = left;
	}

	public String[] getRight() {
		return right;
	}

	public void setRight(String[] right) {
		this.right = right;
	}
	
	public String[] getT() {
		return T;
	}

	public void setT(String[] t) {
		T = t;
	}

	public String[] getNT() {
		return NT;
	}

	public void setNT(String[] nT) {
		NT = nT;
	}

	public String[][] getTable() {
		return table;
	}

	public void setTable(String[][] table) {
		this.table = table;
	}
	
	public String[] getNoLeftG() {
		return noLeftG;
	}

	public void setNoLeftG(String[] noLeftG) {
		this.noLeftG = noLeftG;
	}

	public Model[] getMembers() {
		return members;
	}

	public void setMembers(Model[] members) {
		this.members = members;
	}

	public String[] getFirst() {
		return first;
	}

	public void setFirst(String[] first) {
		this.first = first;
	}

	public String[] getFollow() {
		return follow;
	}

	public void setFollow(String[] follow) {
		this.follow = follow;
	}

	public static void main(String[] args) {
		Scanner scan = new Scanner( System.in );
		int choose = scan.nextInt();
		if ( choose == 0 ){ //i+i*iʽ�ķ�
			
		}else{  //if else ʽ�ķ�
			
		}
		String str[] = {
				"E::=T+E|T-E|T",
				"T::=F*T|F/T|F",
				"F::=(E)|i"
		};
		new YuceTable( str );
	}
}
