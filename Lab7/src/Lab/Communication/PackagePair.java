package Lab.Communication;

import java.net.SocketAddress;

class PackagePair {
    final SocketAddress sender;
    final byte[] pack;
    PackagePair(SocketAddress sender, byte[] pack){
        this.sender=sender;
        this.pack=pack;
    }
}
